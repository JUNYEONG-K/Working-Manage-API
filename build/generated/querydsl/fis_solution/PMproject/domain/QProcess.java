package fis_solution.PMproject.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProcess is a Querydsl query type for Process
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProcess extends EntityPathBase<Process> {

    private static final long serialVersionUID = 1930571581L;

    public static final QProcess process = new QProcess("process");

    public final EnumPath<fis_solution.PMproject.domain.record.fileEnum.F_process> f_process = createEnum("f_process", fis_solution.PMproject.domain.record.fileEnum.F_process.class);

    public final StringPath id = createString("id");

    public QProcess(String variable) {
        super(Process.class, forVariable(variable));
    }

    public QProcess(Path<? extends Process> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProcess(PathMetadata metadata) {
        super(Process.class, metadata);
    }

}

