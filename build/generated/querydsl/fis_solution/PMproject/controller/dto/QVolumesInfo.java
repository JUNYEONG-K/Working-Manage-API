package fis_solution.PMproject.controller.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * fis_solution.PMproject.controller.dto.QVolumesInfo is a Querydsl Projection type for VolumesInfo
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QVolumesInfo extends ConstructorExpression<VolumesInfo> {

    private static final long serialVersionUID = -905663234L;

    public QVolumesInfo(com.querydsl.core.types.Expression<Long> v_id, com.querydsl.core.types.Expression<String> f_labelcode, com.querydsl.core.types.Expression<Integer> v_num, com.querydsl.core.types.Expression<String> f_name, com.querydsl.core.types.Expression<String> f_pyear, com.querydsl.core.types.Expression<String> f_eyear, com.querydsl.core.types.Expression<fis_solution.PMproject.domain.record.fileEnum.F_kperiod> f_kperiod) {
        super(VolumesInfo.class, new Class<?>[]{long.class, String.class, int.class, String.class, String.class, String.class, fis_solution.PMproject.domain.record.fileEnum.F_kperiod.class}, v_id, f_labelcode, v_num, f_name, f_pyear, f_eyear, f_kperiod);
    }

}

