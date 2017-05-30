package com.energyplus.soep;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jmodelica.modelica.compiler.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Source AST for a Component Declaration
 */
public class SrcAstCompDecl
{
    private String className;
    private List<String> qualifiers;
    private String name;
    private String comment;
    private String value;
    private List<SrcAstMod> modifications;
    private String arraySubscripts;
    private String conditionalClause;
    private String constrainingClause;
    private Map<String,String> annotations;
    SrcAstCompDecl(
            String classNameIn,
            List<String> qualifiersIn,
            String nameIn,
            String valueIn
    )
    {
        className = classNameIn;
        qualifiers = qualifiersIn;
        name = nameIn;
        value = valueIn;
    }
    SrcAstCompDecl(
            String classNameIn,
            List<String> qualifiersIn,
            String nameIn,
            String commentIn,
            String valueIn,
            List<SrcAstMod> modificationsIn,
            String arraySubscriptsIn,
            String conditionalClauseIn,
            String constrainingClauseIn,
            Map<String,String> annotationsIn
    )
    {
        className = classNameIn;
        qualifiers = qualifiersIn;
        name = nameIn;
        comment = commentIn;
        value = valueIn;
        modifications = modificationsIn;
        arraySubscripts = arraySubscriptsIn;
        conditionalClause = conditionalClauseIn;
        constrainingClause = constrainingClauseIn;
        annotations = annotationsIn;
    }
    SrcAstCompDecl(SrcAstCompDecl cd)
    {
        className = cd.className;
        qualifiers = App.cloneQualifiers(cd.qualifiers);
        name = cd.name;
        comment = cd.comment;
        value = cd.value;
        modifications = App.cloneModifications(cd.modifications);
    }
    SrcAstCompDecl(ComponentDecl cd)
    {
        if (!cd.isProtected())
        {
            className = cd.getClassName().qualifiedName();
            addQualifiers(cd);
            name = cd.getName().name();
            addComment(cd);
            addValue(cd.getModification());
            addArguments(cd.getModification());
            addArraySubscripts(cd);
            addConditionalClause(cd);
            addConstrainingClause(cd);
            annotations = (new SrcAstAnnotations(cd)).getAnnotations();
        }
    }
    String getClassName()
    {
        return className;
    }
    List<String> getQualifiers()
    {
        return App.cloneQualifiers(qualifiers);
    }
    String getName()
    {
        return name;
    }
    String getComment()
    {
        return comment;
    }
    String getValue()
    {
        return value;
    }
    List<SrcAstMod> getModifications()
    {
        return App.cloneModifications(modifications);
    }
    String getArraySubscripts()
    {
        return arraySubscripts;
    }
    String getConditionalClause()
    {
        return conditionalClause;
    }
    String getConstrainingClause()
    {
        return constrainingClause;
    }
    Map<String,String> getAnnotations()
    {
        return App.cloneAnnotations(annotations);
    }
    private void addQualifiers(ComponentDecl cd)
    {
        ArrayList<String> qs = new ArrayList<>();
        //if (cd.isPublic())
        //    qs.add("public");
        if (cd.isProtected())
            qs.add("protected");
        if (cd.hasVarArraySubscripts())
            qs.add("array");
        if (cd.hasRedeclare())
            qs.add("redeclare");
        if (cd.hasFinal())
            qs.add("final");
        if (cd.hasInner())
            qs.add("inner");
        if (cd.hasOuter())
            qs.add("outer");
        if (cd.hasReplaceable())
            qs.add("replaceable");
        if (cd.isParameter())
            qs.add("parameter");
        if (cd.isConstant())
            qs.add("constant");
        if (cd.isDiscrete())
            qs.add("discrete");
        if (cd.isStream())
            qs.add("stream");
        if (cd.isFlow())
            qs.add("flow");
        if (cd.isInput())
            qs.add("input");
        if (cd.isOutput())
            qs.add("output");
        if (qs.size() > 0)
            qualifiers = qs;
    }
    private void addValue(Modification md)
    {
        if (md == null)
            return;
        if (md instanceof ValueModification)
        {
            ValueModification vm = (ValueModification)md;
            addValue(vm);
        }
        else if (md instanceof CompleteModification)
        {
            ValueModification vm = ((CompleteModification) md).getValueModification();
            addValue(vm);
        }
        else
        {
            System.out.print("Warning! 2017050171118: Unknown modification: " + md.getClass().toString());
        }
    }
    private void addValue(ValueModification vm)
    {
        if (vm == null)
        {
            return;
        }
        addValue(vm.getExp());
    }
    private void addValue(Exp e)
    {
        if (e == null)
        {
            return;
        }
        String valStr = e.toString();
        if (valStr.length() > 0)
        {
            value = valStr;
        }
    }
    private void addArguments(Modification md)
    {
        if (md == null)
        {
            return;
        }
        if (md instanceof ClassModification)
        {
            ClassModification clsMod = (ClassModification)md;
            addArguments(clsMod);
        }
        else if (md instanceof CompleteModification)
        {
            CompleteModification compMod = (CompleteModification)md;
            ClassModification clsMod = compMod.getClassModification();
            addArguments(clsMod);
        }
        else if (md instanceof ValueModification)
        {
            // do nothing. ValueModification has not arguments
            // we match here just so as to make the next clause more valuable
        }
        else
        {
            System.out.println("Warning! 201705171213 Unhandled modification: " + md.getClass().toString());
        }
    }
    private void addArguments(ClassModification cm)
    {
        ArrayList<SrcAstMod> args = new ArrayList<>();
        for (Argument arg : cm.getArguments())
        {
            if (arg instanceof ComponentModification)
            {
                ComponentModification subCompMod = (ComponentModification)arg;
                args.add(new SrcAstMod(subCompMod));
            }
            else if (arg instanceof ClassRedeclare)
            {
                ClassRedeclare cr = (ClassRedeclare)arg;
                args.add(new SrcAstMod(cr));
            }
            else if (arg instanceof ComponentRedeclare)
            {
                ComponentRedeclare cr = (ComponentRedeclare)arg;
                args.add(new SrcAstMod(cr));
            }
            else
            {
                if (arg != null)
                    System.out.println("Warning! 201705241640 unhandled arg: " + arg.getClass().toString());
            }
        }
        if (args.size() > 0)
        {
            modifications = args;
        }
    }
    private void addComment(Comment c)
    {
        if (c == null)
            return;
        StringComment sc = c.getStringComment();
        if (sc != null)
        {
            String strCmnt = sc.getComment();
            if (strCmnt.length() != 0)
                comment = strCmnt;
        }
    }
    private void addComment(ComponentDecl cd)
    {
        addComment(cd.getComment());
    }
    private void addConstrainingClause(ComponentDecl cd)
    {
        if (cd == null)
            return;
        if (cd.hasConstrainingClause())
            constrainingClause = cd.getConstrainingClause().toString();
    }
    private void addConditionalClause(ComponentDecl cd)
    {
        if (cd == null)
            return;
        if (cd.hasConditionalAttribute())
            conditionalClause = cd.getConditionalAttribute().toString();
    }
    private void addArraySubscripts(ComponentDecl cd)
    {
        if (cd == null)
            return;
        if (cd.hasVarArraySubscripts())
        {
            ArraySubscripts as = cd.getVarArraySubscripts();
            if (as != null)
                arraySubscripts = as.toString();
        }
    }
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(7,11)
                .append(className)
                .append(qualifiers)
                .append(name)
                .append(comment)
                .append(value)
                .append(modifications)
                .append(arraySubscripts)
                .append(conditionalClause)
                .append(constrainingClause)
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
        if (!(other instanceof SrcAstCompDecl))
            return false;
        SrcAstCompDecl rhs = (SrcAstCompDecl)other;
        return new EqualsBuilder()
                .append(className, rhs.className)
                .append(qualifiers, rhs.qualifiers)
                .append(name, rhs.name)
                .append(comment, rhs.comment)
                .append(value, rhs.value)
                .append(modifications, rhs.modifications)
                .append(arraySubscripts, rhs.arraySubscripts)
                .append(conditionalClause, rhs.conditionalClause)
                .append(constrainingClause, rhs.constrainingClause)
                .append(annotations, rhs.annotations)
                .isEquals();
    }
    void setValue(String newValue)
    {
        value = newValue;
    }
}
