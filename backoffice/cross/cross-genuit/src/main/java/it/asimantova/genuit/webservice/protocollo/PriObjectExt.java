/**
 * PriObjectExt.java
 *
 * Wrapper di PriObject (Aoggetto generato da Axis)
 * espone metodi facility per l'accesso ai ai campi di un PriObject
 */

package it.asimantova.genuit.webservice.protocollo;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class PriObjectExt implements java.io.Serializable {
    private static final long serialVersionUID = -677296637437187949L;

    PriObject pj;

    public PriObjectExt( PriObject priObj ) {
        if( priObj == null )
            throw new InvalidParameterException( "priObj = null" );
        this.pj = priObj;
/*
        Field[] fs = priObj.getFields();
        FieldExt[] fsx = new FieldExt[ fs.length ];
        for( int i = 0; i < fs.length; i++ ) {
            fsx[i] = new FieldExt( fs[i] );
        }
        priObj.setFields( fsx );
*/
    }

    public FieldExt getField(String fieldName){
        for( Field f : this.pj.getFields() ) {
            if( fieldName.equals( f.getName()) ){
                return new FieldExt( f );
            }
        }
        throw new IndexOutOfBoundsException(fieldName);
    }

    private FieldExt _getField(String fieldName){
        for( Field f : this.pj.getFields() ) {
            if( fieldName.equals( f.getName()) ){
                return new FieldExt( f );
            }
        }
        return null;
    }

    public Object[] getValues(String fieldName ){
        return this.getField( fieldName ).getValue();
    }

    public PriObjectExt[] getValuesObject(String fieldName ){
        Object[] os = this.getField( fieldName ).getValue();
        PriObjectExt[] osx = new PriObjectExt[ os.length ];
        for( int i = 0; i < os.length; i++ ) {
            osx[i] = new PriObjectExt( (PriObject)os[i] );
        }
        return osx;
    }

    public Object getValue(String fieldName, int idxValue){
        Object[] v = this.getField( fieldName ).getValue();
        if( v.length > idxValue )
            return v[idxValue];
        return null;
    }

    public Object getValue(String fieldName){
        return this.getValue(fieldName, 0);
    }

    public int getValueInt(String fieldName, int idxValue){
        Object o = this.getValue(fieldName, idxValue);
        return o != null ? ((Integer) o).intValue() : 0;
    }

    public Integer getValueInteger(String fieldName){
        return this.getValueInteger(fieldName, 0);
    }

    public Integer getValueInteger(String fieldName, int idxValue){
        Object o = this.getValue(fieldName, idxValue);
        return o != null ? (Integer) o : null;
    }

    public int getValueInt(String fieldName){
        return this.getValueInt(fieldName, 0);
    }

    public PriObjectExt getValueObject(String fieldName, int idxValue){
        Object o = this.getValue(fieldName, idxValue);
        return o != null ? new PriObjectExt( (PriObject)o ) : null;
    }

    public PriObjectExt getValueObject(String fieldName){
        return this.getValueObject(fieldName, 0);
    }

    /**
     * torna la toString di un qualsiasi valore o stringa vuota se nullo
     *
     * @param fieldName nome campo
     * @param idxValue indice del valore per i multivalue
     * @return String
     */
    public String getValueString(String fieldName, int idxValue, String ifNull){
        Object o = this.getValue(fieldName, idxValue);
        return o != null ? o.toString() : ifNull;
    }

    public String getValueString(String fieldName, int idxValue){
        return getValueString( fieldName, idxValue, "");
    }


    public String getValueString(String fieldName, String ifNull){
        return getValueString(fieldName, 0, ifNull);
    }

    public String getValueString(String fieldName){
        return getValueString(fieldName, 0);
    }

    public Date getValueDate(String fieldName, int idxValue){
        Object o = this.getValue(fieldName, idxValue);
        if( o == null ) return null;
        if( o instanceof Calendar ) return ((Calendar)o).getTime();

        return (Date)o;
    }

    public Date getValueDate(String fieldName){
        return getValueDate(fieldName, 0);
    }

    public void setValue(String fieldName, Object value ){
        this.setValue( fieldName, value, 0 );
    }

    public void setValue(String fieldName, Object value, int idxValue){
        FieldExt f = this._getField( fieldName );
        if( f == null ){
            this.addFields( FieldExt.create( fieldName, value ) );
            return;
        }
        Object[] v = f.getValue();
        if( idxValue >= v.length || idxValue < 0 ){
            throw new IndexOutOfBoundsException( String.valueOf( idxValue ));
        }
        v[idxValue] = value;
    }

    public FieldExt[] getFieldsExt()
    {
        Field[] fs = this.pj.getFields();
        FieldExt[] fsx = new FieldExt[ fs.length ];
        for( int i = 0; i < fs.length; i++ ) {
            fsx[i] = new FieldExt( fs[i] );
        }
        return fsx;
    }

    public PriObject getPriObject(){
        return this.pj;
    }

    public String getClassName()
    {
        return this.pj.getClassName();
    }

    public Integer getId()
    {
        return this.pj.getId();
    }

    public boolean isFieldNull( String fieldName )
    {
        return getField( fieldName ).isNull();
    }

    public static PriObject create( Integer id, String className, Field ... fields )
    {
        return new PriObject( className, fields, id == null ? 0 : id.intValue());
    }
    public static PriObject create( String className, Field ... fields )
    {
        return create( null, className, fields );
    }

    public void addFields( Field ... fields )
    {
        addFields( this.pj, fields );
    }


    public static void addFields( PriObject priObject , Field ... fields )
    {
        List<Field> list = new ArrayList<Field>();
        Collections.addAll( list, priObject.getFields() );

        for( Field field : fields ) {
            if( field != null ){
                list.add( field );
            }
        }
        priObject.setFields( list.toArray( new Field[list.size()] ) );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "{" )
          .append( this.getClass().getName() )
          .append( " pj=" ).append( this.pj==null ? "null" : this.pj.toString() )
          .append( "}" );

        return sb.toString();
    }

}
