package ConnexionPackage;


/**
* ConnexionPackage/stringsHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Hello.idl
* mardi 24 janvier 2017 09 h 21 CET
*/

public final class stringsHolder implements org.omg.CORBA.portable.Streamable
{
  public String value[] = null;

  public stringsHolder ()
  {
  }

  public stringsHolder (String[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = ConnexionPackage.stringsHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    ConnexionPackage.stringsHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return ConnexionPackage.stringsHelper.type ();
  }

}
