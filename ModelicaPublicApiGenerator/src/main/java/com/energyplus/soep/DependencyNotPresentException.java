package com.energyplus.soep;

/**
 * Exception to be thrown when a dependency is not available for flattening.
 */
public class DependencyNotPresentException extends Throwable
{
    private String missingDependency;
    DependencyNotPresentException(String missingDep)
    {
        super();
        missingDependency = missingDep;
    }
    String getMissingDependency()
    {
        return missingDependency;
    }
}
