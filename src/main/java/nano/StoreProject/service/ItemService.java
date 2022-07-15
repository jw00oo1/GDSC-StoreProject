package nano.StoreProject.service;

import nano.StoreProject.mapper.ItemListMapper;
import nano.StoreProject.vo.ItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class ItemService {
    @Autowired
    ItemListMapper itemListMapper;

    public void addItem(ItemVo item) {
        itemListMapper.itemEnroll(item);
    }

    public int totalItems() {return itemListMapper.itemsGetTotal();}

    public List<ItemVo> getItems() {return itemListMapper.itemsGetList();}

    public ItemVo getItemInfo(int itemId) {return itemListMapper.getItemInfo(itemId);}
}
