package rest;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rest.Cinema;
import rest.Person;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-31T03:37:53")
@StaticMetamodel(Memoir.class)
public class Memoir_ { 

    public static volatile SingularAttribute<Memoir, Cinema> cinemaid;
    public static volatile SingularAttribute<Memoir, Date> datetimewatched;
    public static volatile SingularAttribute<Memoir, BigDecimal> rating;
    public static volatile SingularAttribute<Memoir, Date> moviereleasedate;
    public static volatile SingularAttribute<Memoir, String> comment;
    public static volatile SingularAttribute<Memoir, Person> personid;
    public static volatile SingularAttribute<Memoir, Integer> memoirid;
    public static volatile SingularAttribute<Memoir, String> moviename;

}