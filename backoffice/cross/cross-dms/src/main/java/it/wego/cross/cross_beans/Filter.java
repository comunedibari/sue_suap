package it.wego.cross.cross_beans;

import java.util.ArrayList;

public class Filter{
    public ArrayList<And> and;
}

class And{
    public TypeId typeId;
    public TypeMetadataANNO typeMetadata_ANNO;
    public TypeMetadataTIPOPRATICA typeMetadata_TIPO_PRATICA;
    public TypeMetadataNUMERO typeMetadata_NUMERO;
}

class TypeId{
    public int eq;
}

class TypeMetadataANNO{
    public int eq;
}

class TypeMetadataNUMERO{
    public String eq;
}

class TypeMetadataTIPOPRATICA{
    public String eq;
}