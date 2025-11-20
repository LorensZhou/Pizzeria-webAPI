package nl.novi.pizzeria_webAPI.service;

import nl.novi.pizzeria_webAPI.dto.ItemInputDto;
import nl.novi.pizzeria_webAPI.dto.ItemOutputDto;
import nl.novi.pizzeria_webAPI.exception.InvalidDeletionException;
import nl.novi.pizzeria_webAPI.exception.ResourceNotFoundException;
import nl.novi.pizzeria_webAPI.mapper.ItemMapper;
import nl.novi.pizzeria_webAPI.model.Item;
import nl.novi.pizzeria_webAPI.repository.ItemRepository;
import nl.novi.pizzeria_webAPI.repository.OrderDetailRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepos;
    private final OrderDetailRepository detailRepos;

    public ItemService(ItemRepository itemRepos, OrderDetailRepository detailRepos){
        this.itemRepos = itemRepos;
        this.detailRepos = detailRepos;
    }

    public ItemOutputDto createItem(ItemInputDto itemInputDto) {
        Item item = ItemMapper.toEntity(itemInputDto);
        this.itemRepos.save(item);
        return ItemMapper.toDto(item);
    }

    public void deleteItem(int id) {
        if(!this.itemRepos.existsById(id)){
            throw new ResourceNotFoundException("Item not found with id " + id);
        }
        try {
            this.itemRepos.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            //Deze exception wordt gegooid bij een foreign key constraint
            //bijv. als de item nog gekoppeld is aan een orderdetail
            throw new InvalidDeletionException("Item with id "
                    + id
                    + " can not be deleted, because it is part of an existing order. Delete the item first from the order(s)");
        }
    }

    public List<ItemOutputDto> getAllItems(){
        List<Item>items = this.itemRepos.findAll();

        return items
                .stream()
                .map(ItemMapper::toDto)
                .toList();
    }

    public ItemOutputDto getItemById(int id) {
        return ItemMapper.toDto(this.itemRepos
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Item not found with id " + id)));
    }

    public ItemOutputDto replaceItem(int id, ItemInputDto itemInputDto) {
        Item existingItem = this.itemRepos.findById(id).orElseThrow(()-> new ResourceNotFoundException("Item not found"));

        if(this.detailRepos.existsByItem(existingItem)){
            throw new InvalidDeletionException("Item with id "
                                  + id
                                  + " can not be updated, because it is part of an existing order. "
                                  + "To preserve the integrity of past orders, updates are forbidden.");
        }
        existingItem.setName(itemInputDto.name);
        existingItem.setPrice(itemInputDto.price);

        Item updatedItem = this.itemRepos.save(existingItem);

        return ItemMapper.toDto(updatedItem);
    }
}
