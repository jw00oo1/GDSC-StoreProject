package nano.StoreProject.mapper;

import nano.StoreProject.vo.ItemVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ItemListMapper {
    // 상품 등록
    void itemEnroll(ItemVo item);

    // 상품 정보
    ItemVo getItemInfo(int itemId);

    // 상품 리스트
    List<ItemVo> itemsGetList();

    // 상품 총 개수
    int itemsGetTotal();
}
