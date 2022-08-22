package fis_solution.PMproject.domain.management;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPlan is a Querydsl query type for Plan
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlan extends EntityPathBase<Plan> {

    private static final long serialVersionUID = 696982786L;

    public static final QPlan plan = new QPlan("plan");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath p_boxreorg = createString("p_boxreorg");

    public final StringPath p_boxreorgw = createString("p_boxreorgw");

    public final StringPath p_classify = createString("p_classify");

    public final StringPath p_classifych = createString("p_classifych");

    public final StringPath p_classifychw = createString("p_classifychw");

    public final StringPath p_classifyw = createString("p_classifyw");

    public final StringPath p_etc = createString("p_etc");

    public final StringPath p_etcw = createString("p_etcw");

    public final StringPath p_export = createString("p_export");

    public final StringPath p_exportw = createString("p_exportw");

    public final StringPath p_image = createString("p_image");

    public final StringPath p_imagech = createString("p_imagech");

    public final StringPath p_imagechw = createString("p_imagechw");

    public final StringPath p_imagew = createString("p_imagew");

    public final StringPath p_index = createString("p_index");

    public final StringPath p_indexch = createString("p_indexch");

    public final StringPath p_indexchw = createString("p_indexchw");

    public final StringPath p_indexw = createString("p_indexw");

    public final StringPath p_list = createString("p_list");

    public final StringPath p_listw = createString("p_listw");

    public final StringPath p_loading = createString("p_loading");

    public final StringPath p_loadingw = createString("p_loadingw");

    public final StringPath p_page = createString("p_page");

    public final StringPath p_pagech = createString("p_pagech");

    public final StringPath p_pagechw = createString("p_pagechw");

    public final StringPath p_pagew = createString("p_pagew");

    public final StringPath p_place = createString("p_place");

    public final StringPath p_placew = createString("p_placew");

    public final StringPath p_reorg = createString("p_reorg");

    public final StringPath p_reorgw = createString("p_reorgw");

    public final StringPath p_scan = createString("p_scan");

    public final StringPath p_scanpage = createString("p_scanpage");

    public final StringPath p_scanpagew = createString("p_scanpagew");

    public final StringPath p_scanw = createString("p_scanw");

    public final StringPath p_search = createString("p_search");

    public final StringPath p_searchw = createString("p_searchw");

    public QPlan(String variable) {
        super(Plan.class, forVariable(variable));
    }

    public QPlan(Path<? extends Plan> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlan(PathMetadata metadata) {
        super(Plan.class, metadata);
    }

}

