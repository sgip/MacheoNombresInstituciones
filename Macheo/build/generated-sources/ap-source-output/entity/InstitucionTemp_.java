package entity;

import entity.InstitucionesMacheada;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2014-12-15T08:59:27")
@StaticMetamodel(InstitucionTemp.class)
public class InstitucionTemp_ { 

    public static volatile SingularAttribute<InstitucionTemp, String> codigoSecundario;
    public static volatile SingularAttribute<InstitucionTemp, String> nombreDistrito;
    public static volatile SingularAttribute<InstitucionTemp, String> codigoPrincipal;
    public static volatile SingularAttribute<InstitucionTemp, String> nombreInstitucionOriginal;
    public static volatile SingularAttribute<InstitucionTemp, Integer> tipo;
    public static volatile SingularAttribute<InstitucionTemp, String> codigoDepartamento;
    public static volatile CollectionAttribute<InstitucionTemp, InstitucionesMacheada> institucionesMacheadaCollection;
    public static volatile SingularAttribute<InstitucionTemp, String> nombreIntitucion;
    public static volatile SingularAttribute<InstitucionTemp, Integer> idInstitucion;
    public static volatile SingularAttribute<InstitucionTemp, String> codigoDistrito;
    public static volatile SingularAttribute<InstitucionTemp, String> nombreDepartamento;
    public static volatile CollectionAttribute<InstitucionTemp, InstitucionesMacheada> institucionesMacheadaCollection1;

}