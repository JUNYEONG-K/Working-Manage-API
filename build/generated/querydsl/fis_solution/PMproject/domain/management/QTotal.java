package fis_solution.PMproject.domain.management;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTotal is a Querydsl query type for Total
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTotal extends EntityPathBase<Total> {

    private static final long serialVersionUID = 135431307L;

    public static final QTotal total = new QTotal("total");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath p_export = createString("p_export");

    public final StringPath p_exportw = createString("p_exportw");

    public final StringPath p_image = createString("p_image");

    public final StringPath p_imagew = createString("p_imagew");

    public final StringPath p_index = createString("p_index");

    public final StringPath p_indexch = createString("p_indexch");

    public final StringPath p_indexchw = createString("p_indexchw");

    public final StringPath p_indexw = createString("p_indexw");

    public final StringPath p_scan = createString("p_scan");

    public final StringPath p_scanw = createString("p_scanw");

    public final StringPath p_search = createString("p_search");

    public final StringPath p_searchw = createString("p_searchw");

    public final StringPath p_uploading = createString("p_uploading");

    public final StringPath p_uploadingw = createString("p_uploadingw");

    public QTotal(String variable) {
        super(Total.class, forVariable(variable));
    }

    public QTotal(Path<? extends Total> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTotal(PathMetadata metadata) {
        super(Total.class, metadata);
    }

}

