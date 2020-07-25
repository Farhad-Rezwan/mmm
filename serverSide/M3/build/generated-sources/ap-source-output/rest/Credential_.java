package rest;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rest.Person;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-31T03:37:53")
@StaticMetamodel(Credential.class)
public class Credential_ { 

    public static volatile SingularAttribute<Credential, Date> signupdate;
    public static volatile SingularAttribute<Credential, Integer> credentialid;
    public static volatile SingularAttribute<Credential, Person> personid;
    public static volatile SingularAttribute<Credential, String> passwordhash;
    public static volatile SingularAttribute<Credential, String> username;

}