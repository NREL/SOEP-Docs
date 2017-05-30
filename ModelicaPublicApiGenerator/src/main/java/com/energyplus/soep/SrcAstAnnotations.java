package com.energyplus.soep;


import org.jmodelica.modelica.compiler.ASTNode;
import org.jmodelica.modelica.compiler.AnnotationNode;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class SrcAstAnnotations
{
    private Map<String,String> annotations;
    SrcAstAnnotations(ASTNode ast)
    {
        if (ast == null)
            return;
        Map<String,String> ans = new HashMap<>();
        addAnnotationIfPresent(ans, ast.annotation("Dialog"), "dialog");
        addAnnotationIfPresent(ans, ast.annotation("Placement"), "placement");
        addAnnotationIfPresent(ans, ast.annotation("Documentation/info"), "documentationInfo");
        addAnnotationIfPresent(ans, ast.annotation("Icon"), "icon");
        addAnnotationIfPresent(ans, ast.annotation("Diagram"), "diagram");
        //addAnnotationIfPresent(ans, ast.annotation(), "all");
        if (ans.size() > 0)
            annotations = ans;
    }
    private void addAnnotationIfPresent(Map<String,String> m, AnnotationNode an, String key)
    {
        if (an == null)
            return;
        if (an == AnnotationNode.NO_ANNOTATION)
            return;
        if (an.toString().equals("(no annotation)"))
            return;
        m.put(key, an.toString());
    }
    Map<String,String> getAnnotations()
    {
        return annotations;
    }
}
