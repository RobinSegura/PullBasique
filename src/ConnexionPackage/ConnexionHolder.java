package ConnexionPackage;

/**
* ConnexionHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Hello.idl
* mardi 24 janvier 2017 09 h 21 CET
*/

public final class ConnexionHolder implements org.omg.CORBA.portable.Streamable
{
  public Connexion value = null;

  public ConnexionHolder ()
  {
  }

  public ConnexionHolder (Connexion initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = ConnexionHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    ConnexionHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return ConnexionHelper.type ();
  }

}