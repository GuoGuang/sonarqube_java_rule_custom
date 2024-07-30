package org.springblade.resourceextend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springblade.resourceextend.dto.MateriaTreelDTO;
import org.springblade.resourceextend.entity.BaldexMaterialClass;
import org.springblade.resourceextend.vo.MateriaTreelVo;
import org.springblade.resourceextend.vo.MaterialLibraryExportVO;

import java.util.List;

public interface BladexMaterialClassMapper extends BaseMapper<BaldexMaterialClass> {

	//查询租户下所有的分类id
	@Select("SELECT * FROM t_baldex_material_class WHERE tenant_id = #{tenantId}")
    List<MateriaTreelDTO> treeDate(@Param("tenantId") String tenantId, @Param("materialClassType") Integer materialClassType);
}
