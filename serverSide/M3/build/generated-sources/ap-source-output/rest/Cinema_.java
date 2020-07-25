package rest;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rest.Memoir;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-31T03:37:53")
@StaticMetamodel(Cinema.class)
public class Cinema_ { 

    public static volatile SingularAttribute<Cinema, String> cinemasuburb;
    public static volatile SingularAttribute<Cinema, String> cinemaname;
    public static volatile SingularAttribute<Cinema, Integer> cinemaid;
    public static volatile CollectionAttribute<Cinema, Memoir> memoirCollection;

}