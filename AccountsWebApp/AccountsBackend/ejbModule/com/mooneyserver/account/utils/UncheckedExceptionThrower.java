package com.mooneyserver.account.utils;

/**
 * Utility class to allow exceptions to be thrown without 
 * the user having to check for them. The idea being that 
 * we can hide the checked exceptions from used libraries
 * (failues should be rare) but not just gobble the
 * exceptions in a hidden manner
 * 
 * Refer to the below link for detail on the creation
 * of this class
 * 
 * http://www.gamlor.info/wordpress/2010/02/throwing-checked-excpetions-like-unchecked-exceptions-in-java/
 *
 */
public final class UncheckedExceptionThrower {
    private UncheckedExceptionThrower(){}

    /**
     * Throw exceptions silently.
     * To be used judiciously. More to mask API 
     * exceptions than anything else
     * 
     * @param ex
     * 		<b>Exception</b>
     * 		The Exception to be thrown
     */
    public static void throwUnchecked(final Exception ex){
        // Now we use the 'generic' method. Normally the type T is inferred
        // from the parameters. However you can specify the type also explicit!
        // Now we do just that! We use the RuntimeException as type!
        // That means the throwsUnchecked throws an unchecked exception!
        // Since the types are erased, no type-information is there to prevent this!
    	UncheckedExceptionThrower.<RuntimeException>throwsUnchecked(ex);
    }

    /*
     * Remember, Generics are erased in Java. So this basically throws an Exception. The real
     * Type of T is lost during the compilation
     */
    @SuppressWarnings("unchecked")
	private static <T extends Exception> void throwsUnchecked(Exception toThrow) throws T{
        // Since the type is erased, this cast actually does nothing!!!
        // we can throw any exception
        throw (T) toThrow;
    }
}