package fis_solution.PMproject.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOffice is a Querydsl query type for Office
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOffice extends EntityPathBase<Office> {

    private static final long serialVersionUID = -947528658L;

    public static final QOffice office = new QOffice("office");

    public final ListPath<fis_solution.PMproject.domain.record.Files, fis_solution.PMproject.domain.record.QFiles> fileList = this.<fis_solution.PMproject.domain.record.Files, fis_solution.PMproject.domain.record.QFiles>createList("fileList", fis_solution.PMproject.domain.record.Files.class, fis_solution.PMproject.domain.record.QFiles.class, PathInits.DIRECT2);

    public final StringPath o_code = createString("o_code");

    public final StringPath o_del = createString("o_del");

    public final StringPath o_name = createString("o_name");

    public QOffice(String variable) {
        super(Office.class, forVariable(variable));
    }

    public QOffice(Path<? extends Office> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOffice(PathMetadata metadata) {
        super(Office.class, metadata);
    }

}

