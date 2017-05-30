package com.energyplus.soep;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jmodelica.modelica.compiler.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ExtendsClause for Source AST
 */
public class SrcAstExtClause
{
    private String nameOfExtendedClass;
    private List<SrcAstMod> modifications;
    private Map<String,String> annotations;
    public SrcAstExtClause(
            String nameOfExtendedClassIn,
            List<SrcAstMod> modificationsIn,
            Map<String,String> annotationsIn
    )
    {
        nameOfExtendedClass = nameOfExtendedClassIn;
        modifications = modificationsIn;
        annotations = annotationsIn;
    }
    public SrcAstExtClause(SrcAstExtClause saec)
    {
        nameOfExtendedClass = saec.nameOfExtendedClass;
        modifications = App.cloneModifications(saec.modifications);
        annotations = App.cloneAnnotations(saec.annotations);
    }
    public SrcAstExtClause(ExtendsClause ec)
    {
        nameOfExtendedClass = ec.getSuper().findClassDecl().qualifiedName();
        addArguments(ec);
        annotations = (new SrcAstAnnotations(ec)).getAnnotations();
    }
    private void addArguments(ExtendsClause ec)
    {
        ArrayList<SrcAstMod> args = new ArrayList<>();
        if (ec.hasClassModification())
        {
            for (Argument arg : ec.getClassModification().getArguments())
            {
                if (arg instanceof ComponentModification)
                {
                    args.add(new SrcAstMod((ComponentModification)arg));
                }
                else if (arg instanceof ClassRedeclare)
                {
                    args.add(new SrcAstMod((ClassRedeclare)arg));
                }
                else if (arg instanceof ComponentRedeclare)
                {
                    args.add(new SrcAstMod((ComponentRedeclare)arg));
                }
                else
                {
                    System.out.println("Warning! 201705170940 Unhandled argument: " + arg.getClass().toString());
                }
            }
        }
        if (args.size() > 0)
            modifications = args;
    }
    String getNameOfExtendedClass()
    {
        return nameOfExtendedClass;
    }
    List<SrcAstMod> getModifications()
    {
        return App.cloneModifications(modifications);
    }
    Map<String,String> getAnnotations()
    {
        return App.cloneAnnotations(annotations);
    }
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(21,101)
                .append(nameOfExtendedClass)
                .append(modifications)
                .append(annotations)
                .toHashCode();
    }
    @Override
    public boolean equals(Object other)
    {
        if (other == null)
            return false;
        if (this == other)
            return true;
        if (!(other instanceof SrcAstExtClause))
            return false;
        SrcAstExtClause rhs = (SrcAstExtClause)other;
        return new EqualsBuilder()
                .append(nameOfExtendedClass, rhs.nameOfExtendedClass)
                .append(modifications, rhs.modifications)
                .append(annotations, rhs.annotations)
                .isEquals();
    }
}
