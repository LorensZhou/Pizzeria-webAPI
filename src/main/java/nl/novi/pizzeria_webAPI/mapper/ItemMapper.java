package nl.novi.pizzeria_webAPI.mapper;

import nl.novi.pizzeria_webAPI.dto.ItemInputDto;
import nl.novi.pizzeria_webAPI.dto.ItemOutputDto;
import nl.novi.pizzeria_webAPI.model.Item;

public class ItemMapper {

    public static Item toEntity(ItemInputDto itemInputDto){
        Item item = new Item();
        if(itemInputDto.id != null){
            item.setId(itemInputDto.id);
        }
        item.setName(itemInputDto.name);
        item.setPrice(itemInputDto.price);

        return item;
    }

    public static ItemOutputDto toDto(Item item){
        ItemOutputDto itemOutputDto = new ItemOutputDto();
        itemOutputDto.id = item.getId();
        itemOutputDto.name = item.getName();
        itemOutputDto.price=item.getPrice();

        return itemOutputDto;

    }
}
