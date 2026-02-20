package nl.novi.pizzeria_webAPI.service;

import nl.novi.pizzeria_webAPI.dto.ItemInputDto;
import nl.novi.pizzeria_webAPI.dto.ItemOutputDto;
import nl.novi.pizzeria_webAPI.exception.InvalidDeletionException;
import nl.novi.pizzeria_webAPI.exception.ResourceNotFoundException;
import nl.novi.pizzeria_webAPI.model.Item;
import nl.novi.pizzeria_webAPI.repository.ItemRepository;
import nl.novi.pizzeria_webAPI.repository.OrderDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {
    Item item;

    @BeforeEach
    void setUp() {
        this.item = new Item("Pizza bolognese", 15.50);
        this.item.setId(123);
    }

    @Mock
    ItemRepository itemRepos;

    @Mock
    OrderDetailRepository orderDetailRepos;

    @InjectMocks
    ItemService itemService;

    @Test
    @DisplayName("Should return a new item")
    public void test1() {
        //test voor itemService.createItem()
        //arrange
        ItemInputDto itemInputDto = new ItemInputDto();
        itemInputDto.name = "Pizza tonijn";
        itemInputDto.price = 18.50;
        itemInputDto.id = 1;

        this.item.setName(itemInputDto.name);
        this.item.setPrice(itemInputDto.price);
        this.item.setId(itemInputDto.id);


        // mocken van save() van de repository
        Mockito.when(itemRepos.save(Mockito.any(Item.class))).thenReturn(this.item);

        //act
        ItemOutputDto itemOutputDto = itemService.createItem(itemInputDto);

        //assert
        assertNotNull(itemOutputDto);
        assertEquals("Pizza tonijn", itemOutputDto.name);
        assertEquals(18.50, itemOutputDto.price);
        assertEquals(1, itemOutputDto.id);

        //verifieren of de repository wordt aangeroepen
        Mockito.verify(itemRepos, Mockito.times(1)).save(Mockito.any(Item.class));
    }


    @Test
    @DisplayName("Should delete an existing item")
    public void test2() {
        //test voor itemService.deleteItem()
        //arrange
        int itemId = this.item.getId();
        //Mock de existsById voor een return waarde van true zodat de itemservice geen ResourceNotFoundException gooit
        Mockito.when(itemRepos.existsById(itemId)).thenReturn(true);

        //act
        itemService.deleteItem(itemId);

        //assert
        //omdat de methode void is, verifieren we dat de repository alleen een keer wordt aan geroepen met juiste id
        Mockito.verify(itemRepos, Mockito.times(1)).deleteById(itemId);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when item does not exist when delete")
    public void test3() {
        //test voor itemService.deleteItem() unhappy flow
        //arrange
        int itemId = 999;
        Mockito.when(itemRepos.existsById(itemId)).thenReturn(false);
        //act and assert, verwacht dat de service een exception gaat gooien
        assertThrows(ResourceNotFoundException.class, () -> itemService.deleteItem(itemId));

        //verifieren dat de deleteById werd niet aangeroepen omdat de check heeft gefaald
        Mockito.verify(itemRepos, Mockito.never()).deleteById(anyInt());
    }

    @Test
    @DisplayName("Should throw InvalidDeletionException when DB throws DataIntegrityViolation when trying to delete an item that is in an order")
    public void test4() {
        //test voor itemService.deleteItem() unhappy flow
        //arrange
        int itemId = this.item.getId();

        //Mock de existsById voor een return waarde van true zodat de itemservice geen ResourceNotFoundException gooit
        Mockito.when(itemRepos.existsById(itemId)).thenReturn(true);

        //Mock: (simulatie) vertel de repository om een integrity exception te gooien wanneer deleteById wordt aangeroepen
        //Wij gebruiken doThrow omdat deleteById is een void methode
        Mockito.doThrow(new DataIntegrityViolationException("Foreign key constraint"))
                .when(itemRepos).deleteById(itemId);

        //act and assert
        //verifieren dat de service de repos exception opvangt en gooit de custom InvalidDeletionException
        assertThrows(InvalidDeletionException.class, () -> itemService.deleteItem(itemId));

        //verifieren
        //checken dat deleteById is aangeroepen door itemService.deleteItem()
        Mockito.verify(itemRepos, Mockito.times(1)).deleteById(itemId);
    }

    @Test
    @DisplayName("Should return a list of items")
    public void test5() {
        //itemService.getAllItems()
        //arrange
        Item item1 = new Item("Pizza bolognese", 15.20);
        item1.setId(1);
        Item item2 = new Item("Pizza tonijn", 16.80);
        item2.setId(2);

        List<Item>items = List.of(item1, item2);

        //Mock: de repository van item mocken om lijst van items te retourneren
        Mockito.when(itemRepos.findAll()).thenReturn(items);

        //act
        List<ItemOutputDto> itemOutputDtos = itemService.getAllItems();

        //assert
        assertNotNull(itemOutputDtos);
        assertEquals(2, itemOutputDtos.size());

        assertEquals("Pizza bolognese", itemOutputDtos.get(0).name);
        assertEquals(15.20, itemOutputDtos.get(0).price);

        assertEquals("Pizza tonijn", itemOutputDtos.get(1).name);
        assertEquals(16.80, itemOutputDtos.get(1).price);

        //verifieren
        Mockito.verify(itemRepos, Mockito.times(1)).findAll();
    }


    @Test
    @DisplayName("Should return correct item")
    public void test6(){
        //test voor getItemById()
        //arrange
        //mocken van findById()
        Mockito.when(itemRepos.findById(anyInt())).thenReturn(Optional.of(this.item));

        //act
        ItemOutputDto itemOutputDto = itemService.getItemById(123);

        //assert
        assertEquals("Pizza bolognese", itemOutputDto.name);
        assertEquals(15.50, itemOutputDto.price);
        assertEquals(123, itemOutputDto.id);
    }



    @Test
    @DisplayName("should replace item succesfully when item does not exists in any order")
    public void test7(){
        //itemService.replaceItem()
        //arrange
        //de id van this.item = 123 en we willen this.item (the old pizza) updaten
        int itemId = 123;
        ItemInputDto itemInputDto = new ItemInputDto();
        itemInputDto.name = "Updated Pizza";
        itemInputDto.price = 17.60;

        //Mock: vind de bestaande item
        Mockito.when(itemRepos.findById(itemId)).thenReturn(Optional.of(this.item));

        //Mock: checken dat het niet in een order zit
        Mockito.when(orderDetailRepos.existsByItem(this.item)).thenReturn(false);

        //Mock: de save actie (retourneert de updated item)
        Mockito.when(itemRepos.save(Mockito.any(Item.class))).thenAnswer(i->i.getArguments()[0]);

        //act
        ItemOutputDto itemOutputDto = itemService.replaceItem(itemId, itemInputDto);

        //assert
        assertEquals("Updated Pizza", itemOutputDto.name);
        assertEquals(17.60, itemOutputDto.price);
        Mockito.verify(itemRepos, Mockito.times(1)).save(this.item);
    }

    @Test
    @DisplayName("Should throw exception when trying to replace an item that is in an order")
    public void test8() {
        //itemService.replaceItem() unhappy flow
        //arrange
        int itemId = 123;
        ItemInputDto itemInputDto = new ItemInputDto();
        itemInputDto.name = "New Pizza";
        itemInputDto.price = 17.60;

        Mockito.when(itemRepos.findById(itemId)).thenReturn(Optional.of(this.item));

        //Mock: simuleren dat de item een onderdeel is van een order
        Mockito.when(orderDetailRepos.existsByItem(this.item)).thenReturn(true);

        //act and assert
        assertThrows(InvalidDeletionException.class, () -> itemService.replaceItem(itemId, itemInputDto));

        //verifieren dat save() mag nooit worden aangeroepen als er een exception wordt gegooid
        Mockito.verify(itemRepos, Mockito.never()).save(Mockito.any(Item.class));
    }


}
