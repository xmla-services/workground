package org.eclipse.daanse.function;

public class FunUtil {

    static final String[] emptyStringArray = new String[ 0 ];

    private FunUtil() {
        // constructor
    }

    public static int[] decodeParameterCategories(String flags) {
        int[] parameterCategories = new int[ flags.length() - 2 ];
        for ( int i = 0; i < parameterCategories.length; i++ ) {
            parameterCategories[ i ] = FunUtil.decodeCategory( flags, i + 2 );
        }
        return parameterCategories;
    }

    /**
     * Decodes the {@code offset}th character of an encoded method signature into a type category.
     *
     * <p>The codes are:
     * <table border="1">
     *
     * <tr><td>a</td><td>{@link Category#ARRAY}</td></tr>
     *
     * <tr><td>d</td><td>{@link Category#DIMENSION}</td></tr>
     *
     * <tr><td>h</td><td>{@link Category#HIERARCHY}</td></tr>
     *
     * <tr><td>l</td><td>{@link Category#LEVEL}</td></tr>
     *
     * <tr><td>b</td><td>{@link Category#LOGICAL}</td></tr>
     *
     * <tr><td>m</td><td>{@link Category#MEMBER}</td></tr>
     *
     * <tr><td>N</td><td>Constant {@link Category#NUMERIC}</td></tr>
     *
     * <tr><td>n</td><td>{@link Category#NUMERIC}</td></tr>
     *
     * <tr><td>x</td><td>{@link Category#SET}</td></tr>
     *
     * <tr><td>#</td><td>Constant {@link Category#STRING}</td></tr>
     *
     * <tr><td>S</td><td>{@link Category#STRING}</td></tr>
     *
     * <tr><td>t</td><td>{@link Category#TUPLE}</td></tr>
     *
     * <tr><td>v</td><td>{@link Category#VALUE}</td></tr>
     *
     * <tr><td>y</td><td>{@link Category#SYMBOL}</td></tr>
     *
     * </table>
     *
     * @param flags  Encoded signature string
     * @param offset 0-based offset of character within string
     * @return A {@link Category}
     */
    public static int decodeCategory( String flags, int offset ) {
        char c = flags.charAt( offset );
        switch ( c ) {
            case 'a':
                return Category.ARRAY;
            case 'd':
                return Category.DIMENSION;
            case 'h':
                return Category.HIERARCHY;
            case 'l':
                return Category.LEVEL;
            case 'b':
                return Category.LOGICAL;
            case 'm':
                return Category.MEMBER;
            case 'N':
                return Category.NUMERIC | Category.CONSTANT;
            case 'n':
                return Category.NUMERIC;
            case 'I':
                return Category.NUMERIC | Category.INTEGER | Category.CONSTANT;
            case 'i':
                return Category.NUMERIC | Category.INTEGER;
            case 'x':
                return Category.SET;
            case '#':
                return Category.STRING | Category.CONSTANT;
            case 'S':
                return Category.STRING;
            case 't':
                return Category.TUPLE;
            case 'v':
                return Category.VALUE;
            case 'y':
                return Category.SYMBOL;
            case 'U':
                return Category.NULL;
            case 'e':
                return Category.EMPTY;
            case 'D':
                return Category.DATE_TIME;
            default:
                throw Util.newInternal(
                    new StringBuilder("unknown type code '").append(c)
                        .append("' in string '").append(flags).append("'").toString() );
        }
    }

    /**
     * Decodes the signature of a function into a category code which describes the return type of the operator.
     *
     * <p>For example, <code>decodeReturnType("fnx")</code> returns
     * <code>{@link Category#NUMERIC}</code>, indicating this function has a
     * numeric return value.
     *
     * @param flags The signature of an operator, as used by the {@code flags} parameter used to construct a {@link
     *              FunDefBase}.
     * @return An array {@link Category} codes.
     */
    public static int decodeReturnCategory( String flags ) {
        final int returnCategory = FunUtil.decodeCategory( flags, 1 );
        if ( ( returnCategory & Category.MASK) != returnCategory ) {
            throw Util.newInternal( new StringBuilder("bad return code flag in flags '")
                .append(flags).append("'").toString() );
        }
        return returnCategory;
    }

    /**
     * Decodes the syntactic type of an operator.
     *
     * @param flags A encoded string which represents an operator signature, as used by the {@code flags} parameter used
     *              to construct a {@link FunDefBase}.
     * @return A {@link Syntax}
     */
    public static Syntax decodeSyntacticType( String flags ) {
        char c = flags.charAt( 0 );
        switch ( c ) {
            case 'p':
                return Syntax.Property;
            case 'f':
                return Syntax.Function;
            case 'm':
                return Syntax.Method;
            case 'i':
                return Syntax.Infix;
            case 'P':
                return Syntax.Prefix;
            case 'Q':
                return Syntax.Postfix;
            case 'I':
                return Syntax.Internal;
            default:
                throw Util.newInternal(
                    new StringBuilder("unknown syntax code '").append(c).append("' in string '")
                        .append(flags).append("'").toString() );
        }
    }

    static FunDef createDummyFunDef(
        FunctionResolver resolver,
        int returnCategory,
        Exp[] args ) {
        final int[] argCategories = ExpBase.getTypes( args );
        return new FunDefBase( resolver, returnCategory, argCategories ) {
        };
    }

    /**
     * Creates an exception which indicates that an error has occurred while executing a given function.
     *
     * @param funDef  Function being executed
     * @param message Explanatory message
     * @return Exception that can be used as a cell result
     */
    public static RuntimeException newEvalException(
        FunDef funDef,
        String message ) {
        //XOMUtil.discard( funDef ); // TODO: use this
        return new RuntimeException( message );
    }


}
