// $ANTLR 3.4 D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g 2012-03-25 19:32:26

	package aurelienribon.ui.css.antlr;
	
	import java.util.List;
	import java.util.ArrayList;
	import java.util.Map;
	import java.util.HashMap;
	import java.util.LinkedHashMap;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class CssParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "COLON", "COMMA", "COMMENT", "DOT", "EQUAL", "FALSE", "HASH", "IDENT", "LBRACE", "LBRACKET", "LCURLY", "NAMECHAR", "NAMESTART", "NULL", "NUMBER", "RBRACE", "RBRACKET", "RCURLY", "SEMI", "STAR", "STRING", "TRUE", "WS", "'>'"
    };

    public static final int EOF=-1;
    public static final int T__27=27;
    public static final int COLON=4;
    public static final int COMMA=5;
    public static final int COMMENT=6;
    public static final int DOT=7;
    public static final int EQUAL=8;
    public static final int FALSE=9;
    public static final int HASH=10;
    public static final int IDENT=11;
    public static final int LBRACE=12;
    public static final int LBRACKET=13;
    public static final int LCURLY=14;
    public static final int NAMECHAR=15;
    public static final int NAMESTART=16;
    public static final int NULL=17;
    public static final int NUMBER=18;
    public static final int RBRACE=19;
    public static final int RBRACKET=20;
    public static final int RCURLY=21;
    public static final int SEMI=22;
    public static final int STAR=23;
    public static final int STRING=24;
    public static final int TRUE=25;
    public static final int WS=26;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public CssParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public CssParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String[] getTokenNames() { return CssParser.tokenNames; }
    public String getGrammarFileName() { return "D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g"; }


    	@Override
    	public void emitErrorMessage(String msg) {
    		throw new RuntimeException(msg);
    	}
    		
    	// ---------------------------------------------------------------------------
    	
    	public static class Rule {
    		public List<Selector> selectors = new ArrayList<Selector>();
    		public Map<String, List<Object>> declarations = new LinkedHashMap<String, List<Object>>();
    	}
    	
    	public static class Selector {
    		public String str;
    	}
    	
    	public static class Function {
    		public String name;
    		public List<Object> params = new ArrayList<Object>();
    	}
    	
    	public static class Color {
    		public String str;
    	}
    	
    	// ---------------------------------------------------------------------------
    	
    	public final List<Rule> rules = new ArrayList<Rule>();
    	private Rule tRule;
    	private List<Object> tParams;
    		
    	private void beginRule() {
    		tRule = new Rule();
    		rules.add(tRule);
    	}
    	
    	private void selector(String str) {
    		Selector sel = new Selector();
    		sel.str = str;
    		tRule.selectors.add(sel);
    	}
    	
    	private void beginDecl(String prop) {
    		tParams = new ArrayList<Object>();
    		tRule.declarations.put(prop, tParams);
    	}
    	
    	private void param(Object o) {
    		tParams.add(o);
    	}
    	
    	// ---------------------------------------------------------------------------
    	
    	private Object number(String str) {
    		if (str.contains(".")) return Float.parseFloat(str);
    		return Integer.parseInt(str);
    	}



    // $ANTLR start "stylesheet"
    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:88:1: stylesheet : ( rule )* ;
    public final void stylesheet() throws RecognitionException {
        try {
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:89:2: ( ( rule )* )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:89:4: ( rule )*
            {
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:89:4: ( rule )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==DOT||(LA1_0 >= HASH && LA1_0 <= IDENT)||LA1_0==STAR) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:89:4: rule
            	    {
            	    pushFollow(FOLLOW_rule_in_stylesheet45);
            	    rule();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "stylesheet"



    // $ANTLR start "rule"
    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:92:1: rule : selector_list '{' ( declaration )* '}' ;
    public final void rule() throws RecognitionException {
        beginRule();
        try {
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:94:2: ( selector_list '{' ( declaration )* '}' )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:94:4: selector_list '{' ( declaration )* '}'
            {
            pushFollow(FOLLOW_selector_list_in_rule63);
            selector_list();

            state._fsp--;
            if (state.failed) return ;

            match(input,LCURLY,FOLLOW_LCURLY_in_rule65); if (state.failed) return ;

            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:94:22: ( declaration )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==IDENT) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:94:22: declaration
            	    {
            	    pushFollow(FOLLOW_declaration_in_rule67);
            	    declaration();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            match(input,RCURLY,FOLLOW_RCURLY_in_rule70); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "rule"



    // $ANTLR start "selector_list"
    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:97:1: selector_list : selector ( COMMA selector )* ;
    public final void selector_list() throws RecognitionException {
        try {
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:98:2: ( selector ( COMMA selector )* )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:98:4: selector ( COMMA selector )*
            {
            pushFollow(FOLLOW_selector_in_selector_list83);
            selector();

            state._fsp--;
            if (state.failed) return ;

            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:98:13: ( COMMA selector )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==COMMA) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:98:14: COMMA selector
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_selector_list86); if (state.failed) return ;

            	    pushFollow(FOLLOW_selector_in_selector_list88);
            	    selector();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "selector_list"


    public static class selector_return extends ParserRuleReturnScope {
    };


    // $ANTLR start "selector"
    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:101:1: selector : selector_atom ( ( '>' )? selector_atom )* ( selector_pseudo )? ;
    public final CssParser.selector_return selector() throws RecognitionException {
        CssParser.selector_return retval = new CssParser.selector_return();
        retval.start = input.LT(1);


        try {
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:103:2: ( selector_atom ( ( '>' )? selector_atom )* ( selector_pseudo )? )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:103:4: selector_atom ( ( '>' )? selector_atom )* ( selector_pseudo )?
            {
            pushFollow(FOLLOW_selector_atom_in_selector107);
            selector_atom();

            state._fsp--;
            if (state.failed) return retval;

            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:103:18: ( ( '>' )? selector_atom )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==DOT||(LA5_0 >= HASH && LA5_0 <= IDENT)||LA5_0==STAR||LA5_0==27) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:103:19: ( '>' )? selector_atom
            	    {
            	    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:103:19: ( '>' )?
            	    int alt4=2;
            	    int LA4_0 = input.LA(1);

            	    if ( (LA4_0==27) ) {
            	        alt4=1;
            	    }
            	    switch (alt4) {
            	        case 1 :
            	            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:103:19: '>'
            	            {
            	            match(input,27,FOLLOW_27_in_selector110); if (state.failed) return retval;

            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_selector_atom_in_selector113);
            	    selector_atom();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:103:40: ( selector_pseudo )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==COLON) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:103:40: selector_pseudo
                    {
                    pushFollow(FOLLOW_selector_pseudo_in_selector117);
                    selector_pseudo();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {selector(input.toString(retval.start,input.LT(-1)));}
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "selector"



    // $ANTLR start "selector_atom"
    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:106:1: selector_atom : ( ( IDENT | STAR ) ( ( HASH | DOT )=> ( HASH | DOT IDENT ) )* | ( ( HASH | DOT )=> ( HASH | DOT IDENT ) )+ );
    public final void selector_atom() throws RecognitionException {
        try {
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:107:2: ( ( IDENT | STAR ) ( ( HASH | DOT )=> ( HASH | DOT IDENT ) )* | ( ( HASH | DOT )=> ( HASH | DOT IDENT ) )+ )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==IDENT||LA11_0==STAR) ) {
                alt11=1;
            }
            else if ( (LA11_0==DOT||LA11_0==HASH) ) {
                alt11=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;

            }
            switch (alt11) {
                case 1 :
                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:107:4: ( IDENT | STAR ) ( ( HASH | DOT )=> ( HASH | DOT IDENT ) )*
                    {
                    if ( input.LA(1)==IDENT||input.LA(1)==STAR ) {
                        input.consume();
                        state.errorRecovery=false;
                        state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:107:19: ( ( HASH | DOT )=> ( HASH | DOT IDENT ) )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0==HASH) ) {
                            int LA8_2 = input.LA(2);

                            if ( (synpred1_Css()) ) {
                                alt8=1;
                            }


                        }
                        else if ( (LA8_0==DOT) ) {
                            int LA8_3 = input.LA(2);

                            if ( (LA8_3==IDENT) ) {
                                int LA8_5 = input.LA(3);

                                if ( (synpred1_Css()) ) {
                                    alt8=1;
                                }


                            }


                        }


                        switch (alt8) {
                    	case 1 :
                    	    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:107:20: ( HASH | DOT )=> ( HASH | DOT IDENT )
                    	    {
                    	    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:107:35: ( HASH | DOT IDENT )
                    	    int alt7=2;
                    	    int LA7_0 = input.LA(1);

                    	    if ( (LA7_0==HASH) ) {
                    	        alt7=1;
                    	    }
                    	    else if ( (LA7_0==DOT) ) {
                    	        alt7=2;
                    	    }
                    	    else {
                    	        if (state.backtracking>0) {state.failed=true; return ;}
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 7, 0, input);

                    	        throw nvae;

                    	    }
                    	    switch (alt7) {
                    	        case 1 :
                    	            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:107:36: HASH
                    	            {
                    	            match(input,HASH,FOLLOW_HASH_in_selector_atom149); if (state.failed) return ;

                    	            }
                    	            break;
                    	        case 2 :
                    	            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:107:43: DOT IDENT
                    	            {
                    	            match(input,DOT,FOLLOW_DOT_in_selector_atom153); if (state.failed) return ;

                    	            match(input,IDENT,FOLLOW_IDENT_in_selector_atom155); if (state.failed) return ;

                    	            }
                    	            break;

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop8;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:108:4: ( ( HASH | DOT )=> ( HASH | DOT IDENT ) )+
                    {
                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:108:4: ( ( HASH | DOT )=> ( HASH | DOT IDENT ) )+
                    int cnt10=0;
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0==HASH) ) {
                            int LA10_2 = input.LA(2);

                            if ( (synpred2_Css()) ) {
                                alt10=1;
                            }


                        }
                        else if ( (LA10_0==DOT) ) {
                            int LA10_3 = input.LA(2);

                            if ( (synpred2_Css()) ) {
                                alt10=1;
                            }


                        }


                        switch (alt10) {
                    	case 1 :
                    	    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:108:5: ( HASH | DOT )=> ( HASH | DOT IDENT )
                    	    {
                    	    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:108:20: ( HASH | DOT IDENT )
                    	    int alt9=2;
                    	    int LA9_0 = input.LA(1);

                    	    if ( (LA9_0==HASH) ) {
                    	        alt9=1;
                    	    }
                    	    else if ( (LA9_0==DOT) ) {
                    	        alt9=2;
                    	    }
                    	    else {
                    	        if (state.backtracking>0) {state.failed=true; return ;}
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 9, 0, input);

                    	        throw nvae;

                    	    }
                    	    switch (alt9) {
                    	        case 1 :
                    	            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:108:21: HASH
                    	            {
                    	            match(input,HASH,FOLLOW_HASH_in_selector_atom174); if (state.failed) return ;

                    	            }
                    	            break;
                    	        case 2 :
                    	            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:108:28: DOT IDENT
                    	            {
                    	            match(input,DOT,FOLLOW_DOT_in_selector_atom178); if (state.failed) return ;

                    	            match(input,IDENT,FOLLOW_IDENT_in_selector_atom180); if (state.failed) return ;

                    	            }
                    	            break;

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt10 >= 1 ) break loop10;
                    	    if (state.backtracking>0) {state.failed=true; return ;}
                                EarlyExitException eee =
                                    new EarlyExitException(10, input);
                                throw eee;
                        }
                        cnt10++;
                    } while (true);


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "selector_atom"



    // $ANTLR start "selector_pseudo"
    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:111:1: selector_pseudo : COLON IDENT ;
    public final void selector_pseudo() throws RecognitionException {
        try {
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:112:2: ( COLON IDENT )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:112:4: COLON IDENT
            {
            match(input,COLON,FOLLOW_COLON_in_selector_pseudo195); if (state.failed) return ;

            match(input,IDENT,FOLLOW_IDENT_in_selector_pseudo197); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "selector_pseudo"



    // $ANTLR start "selector_attr"
    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:115:1: selector_attr : LBRACKET IDENT EQUAL STRING RBRACKET ;
    public final void selector_attr() throws RecognitionException {
        try {
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:116:2: ( LBRACKET IDENT EQUAL STRING RBRACKET )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:116:4: LBRACKET IDENT EQUAL STRING RBRACKET
            {
            match(input,LBRACKET,FOLLOW_LBRACKET_in_selector_attr209); if (state.failed) return ;

            match(input,IDENT,FOLLOW_IDENT_in_selector_attr211); if (state.failed) return ;

            match(input,EQUAL,FOLLOW_EQUAL_in_selector_attr213); if (state.failed) return ;

            match(input,STRING,FOLLOW_STRING_in_selector_attr215); if (state.failed) return ;

            match(input,RBRACKET,FOLLOW_RBRACKET_in_selector_attr217); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "selector_attr"



    // $ANTLR start "declaration"
    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:119:1: declaration : IDENT COLON ( param )+ SEMI ;
    public final void declaration() throws RecognitionException {
        Token IDENT1=null;
        Object param2 =null;


        try {
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:120:2: ( IDENT COLON ( param )+ SEMI )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:120:4: IDENT COLON ( param )+ SEMI
            {
            IDENT1=(Token)match(input,IDENT,FOLLOW_IDENT_in_declaration229); if (state.failed) return ;

            if ( state.backtracking==0 ) {beginDecl((IDENT1!=null?IDENT1.getText():null));}

            match(input,COLON,FOLLOW_COLON_in_declaration243); if (state.failed) return ;

            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:121:9: ( param )+
            int cnt12=0;
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0 >= FALSE && LA12_0 <= IDENT)||(LA12_0 >= NULL && LA12_0 <= NUMBER)||(LA12_0 >= STRING && LA12_0 <= TRUE)) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:121:10: param
            	    {
            	    pushFollow(FOLLOW_param_in_declaration246);
            	    param2=param();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    if ( state.backtracking==0 ) {param(param2);}

            	    }
            	    break;

            	default :
            	    if ( cnt12 >= 1 ) break loop12;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(12, input);
                        throw eee;
                }
                cnt12++;
            } while (true);


            match(input,SEMI,FOLLOW_SEMI_in_declaration256); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "declaration"



    // $ANTLR start "param"
    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:125:1: param returns [Object o] : ( function | param_atom );
    public final Object param() throws RecognitionException {
        Object o = null;


        Function function3 =null;

        Object param_atom4 =null;


        try {
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:126:2: ( function | param_atom )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==IDENT) ) {
                int LA13_1 = input.LA(2);

                if ( (LA13_1==LBRACE) ) {
                    alt13=1;
                }
                else if ( (LA13_1==COMMA||(LA13_1 >= FALSE && LA13_1 <= IDENT)||(LA13_1 >= NULL && LA13_1 <= RBRACE)||LA13_1==SEMI||(LA13_1 >= STRING && LA13_1 <= TRUE)) ) {
                    alt13=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return o;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 1, input);

                    throw nvae;

                }
            }
            else if ( ((LA13_0 >= FALSE && LA13_0 <= HASH)||(LA13_0 >= NULL && LA13_0 <= NUMBER)||(LA13_0 >= STRING && LA13_0 <= TRUE)) ) {
                alt13=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return o;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;

            }
            switch (alt13) {
                case 1 :
                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:126:4: function
                    {
                    pushFollow(FOLLOW_function_in_param272);
                    function3=function();

                    state._fsp--;
                    if (state.failed) return o;

                    if ( state.backtracking==0 ) {o = function3;}

                    }
                    break;
                case 2 :
                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:127:4: param_atom
                    {
                    pushFollow(FOLLOW_param_atom_in_param282);
                    param_atom4=param_atom();

                    state._fsp--;
                    if (state.failed) return o;

                    if ( state.backtracking==0 ) {o = param_atom4;}

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return o;
    }
    // $ANTLR end "param"



    // $ANTLR start "function"
    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:130:1: function returns [Function f] : IDENT LBRACE (p1= param ( COMMA p2= param )* )? RBRACE ;
    public final Function function() throws RecognitionException {
        Function f = null;


        Token IDENT5=null;
        Object p1 =null;

        Object p2 =null;


        try {
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:131:2: ( IDENT LBRACE (p1= param ( COMMA p2= param )* )? RBRACE )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:131:4: IDENT LBRACE (p1= param ( COMMA p2= param )* )? RBRACE
            {
            IDENT5=(Token)match(input,IDENT,FOLLOW_IDENT_in_function302); if (state.failed) return f;

            match(input,LBRACE,FOLLOW_LBRACE_in_function304); if (state.failed) return f;

            if ( state.backtracking==0 ) {f = new Function(); f.name = (IDENT5!=null?IDENT5.getText():null);}

            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:132:3: (p1= param ( COMMA p2= param )* )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( ((LA15_0 >= FALSE && LA15_0 <= IDENT)||(LA15_0 >= NULL && LA15_0 <= NUMBER)||(LA15_0 >= STRING && LA15_0 <= TRUE)) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:132:4: p1= param ( COMMA p2= param )*
                    {
                    pushFollow(FOLLOW_param_in_function317);
                    p1=param();

                    state._fsp--;
                    if (state.failed) return f;

                    if ( state.backtracking==0 ) {f.params.add(p1);}

                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:133:3: ( COMMA p2= param )*
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( (LA14_0==COMMA) ) {
                            alt14=1;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:133:4: COMMA p2= param
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_function331); if (state.failed) return f;

                    	    pushFollow(FOLLOW_param_in_function335);
                    	    p2=param();

                    	    state._fsp--;
                    	    if (state.failed) return f;

                    	    if ( state.backtracking==0 ) {f.params.add(p2);}

                    	    }
                    	    break;

                    	default :
                    	    break loop14;
                        }
                    } while (true);


                    }
                    break;

            }


            match(input,RBRACE,FOLLOW_RBRACE_in_function347); if (state.failed) return f;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return f;
    }
    // $ANTLR end "function"



    // $ANTLR start "param_atom"
    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:137:1: param_atom returns [Object o] : ( NUMBER | TRUE | FALSE | NULL | STRING | IDENT | HASH );
    public final Object param_atom() throws RecognitionException {
        Object o = null;


        Token NUMBER6=null;
        Token STRING7=null;
        Token IDENT8=null;
        Token HASH9=null;

        try {
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:138:2: ( NUMBER | TRUE | FALSE | NULL | STRING | IDENT | HASH )
            int alt16=7;
            switch ( input.LA(1) ) {
            case NUMBER:
                {
                alt16=1;
                }
                break;
            case TRUE:
                {
                alt16=2;
                }
                break;
            case FALSE:
                {
                alt16=3;
                }
                break;
            case NULL:
                {
                alt16=4;
                }
                break;
            case STRING:
                {
                alt16=5;
                }
                break;
            case IDENT:
                {
                alt16=6;
                }
                break;
            case HASH:
                {
                alt16=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return o;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;

            }

            switch (alt16) {
                case 1 :
                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:138:4: NUMBER
                    {
                    NUMBER6=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_param_atom363); if (state.failed) return o;

                    if ( state.backtracking==0 ) {o = number((NUMBER6!=null?NUMBER6.getText():null));}

                    }
                    break;
                case 2 :
                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:139:4: TRUE
                    {
                    match(input,TRUE,FOLLOW_TRUE_in_param_atom371); if (state.failed) return o;

                    if ( state.backtracking==0 ) {o = Boolean.TRUE;}

                    }
                    break;
                case 3 :
                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:140:4: FALSE
                    {
                    match(input,FALSE,FOLLOW_FALSE_in_param_atom381); if (state.failed) return o;

                    if ( state.backtracking==0 ) {o = Boolean.FALSE;}

                    }
                    break;
                case 4 :
                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:141:4: NULL
                    {
                    match(input,NULL,FOLLOW_NULL_in_param_atom390); if (state.failed) return o;

                    if ( state.backtracking==0 ) {o = null;}

                    }
                    break;
                case 5 :
                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:142:4: STRING
                    {
                    STRING7=(Token)match(input,STRING,FOLLOW_STRING_in_param_atom400); if (state.failed) return o;

                    if ( state.backtracking==0 ) {o = (STRING7!=null?STRING7.getText():null).substring(1, (STRING7!=null?STRING7.getText():null).length()-1);}

                    }
                    break;
                case 6 :
                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:143:4: IDENT
                    {
                    IDENT8=(Token)match(input,IDENT,FOLLOW_IDENT_in_param_atom408); if (state.failed) return o;

                    if ( state.backtracking==0 ) {o = (IDENT8!=null?IDENT8.getText():null);}

                    }
                    break;
                case 7 :
                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:144:4: HASH
                    {
                    HASH9=(Token)match(input,HASH,FOLLOW_HASH_in_param_atom417); if (state.failed) return o;

                    if ( state.backtracking==0 ) {o = new Color(); ((Color)o).str = (HASH9!=null?HASH9.getText():null);}

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return o;
    }
    // $ANTLR end "param_atom"

    // $ANTLR start synpred1_Css
    public final void synpred1_Css_fragment() throws RecognitionException {
        // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:107:20: ( HASH | DOT )
        // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:
        {
        if ( input.LA(1)==DOT||input.LA(1)==HASH ) {
            input.consume();
            state.errorRecovery=false;
            state.failed=false;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            MismatchedSetException mse = new MismatchedSetException(null,input);
            throw mse;
        }


        }

    }
    // $ANTLR end synpred1_Css

    // $ANTLR start synpred2_Css
    public final void synpred2_Css_fragment() throws RecognitionException {
        // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:108:5: ( HASH | DOT )
        // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:
        {
        if ( input.LA(1)==DOT||input.LA(1)==HASH ) {
            input.consume();
            state.errorRecovery=false;
            state.failed=false;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            MismatchedSetException mse = new MismatchedSetException(null,input);
            throw mse;
        }


        }

    }
    // $ANTLR end synpred2_Css

    // Delegated rules

    public final boolean synpred1_Css() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_Css_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_Css() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_Css_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


 

    public static final BitSet FOLLOW_rule_in_stylesheet45 = new BitSet(new long[]{0x0000000000800C82L});
    public static final BitSet FOLLOW_selector_list_in_rule63 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LCURLY_in_rule65 = new BitSet(new long[]{0x0000000000200800L});
    public static final BitSet FOLLOW_declaration_in_rule67 = new BitSet(new long[]{0x0000000000200800L});
    public static final BitSet FOLLOW_RCURLY_in_rule70 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_selector_in_selector_list83 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_COMMA_in_selector_list86 = new BitSet(new long[]{0x0000000000800C80L});
    public static final BitSet FOLLOW_selector_in_selector_list88 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_selector_atom_in_selector107 = new BitSet(new long[]{0x0000000008800C92L});
    public static final BitSet FOLLOW_27_in_selector110 = new BitSet(new long[]{0x0000000000800C80L});
    public static final BitSet FOLLOW_selector_atom_in_selector113 = new BitSet(new long[]{0x0000000008800C92L});
    public static final BitSet FOLLOW_selector_pseudo_in_selector117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_selector_atom130 = new BitSet(new long[]{0x0000000000000482L});
    public static final BitSet FOLLOW_HASH_in_selector_atom149 = new BitSet(new long[]{0x0000000000000482L});
    public static final BitSet FOLLOW_DOT_in_selector_atom153 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_IDENT_in_selector_atom155 = new BitSet(new long[]{0x0000000000000482L});
    public static final BitSet FOLLOW_HASH_in_selector_atom174 = new BitSet(new long[]{0x0000000000000482L});
    public static final BitSet FOLLOW_DOT_in_selector_atom178 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_IDENT_in_selector_atom180 = new BitSet(new long[]{0x0000000000000482L});
    public static final BitSet FOLLOW_COLON_in_selector_pseudo195 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_IDENT_in_selector_pseudo197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACKET_in_selector_attr209 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_IDENT_in_selector_attr211 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_EQUAL_in_selector_attr213 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_STRING_in_selector_attr215 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RBRACKET_in_selector_attr217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_declaration229 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_COLON_in_declaration243 = new BitSet(new long[]{0x0000000003060E00L});
    public static final BitSet FOLLOW_param_in_declaration246 = new BitSet(new long[]{0x0000000003460E00L});
    public static final BitSet FOLLOW_SEMI_in_declaration256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_in_param272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_param_atom_in_param282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_function302 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LBRACE_in_function304 = new BitSet(new long[]{0x00000000030E0E00L});
    public static final BitSet FOLLOW_param_in_function317 = new BitSet(new long[]{0x0000000000080020L});
    public static final BitSet FOLLOW_COMMA_in_function331 = new BitSet(new long[]{0x0000000003060E00L});
    public static final BitSet FOLLOW_param_in_function335 = new BitSet(new long[]{0x0000000000080020L});
    public static final BitSet FOLLOW_RBRACE_in_function347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_param_atom363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_param_atom371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_param_atom381 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_in_param_atom390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_param_atom400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_param_atom408 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_param_atom417 = new BitSet(new long[]{0x0000000000000002L});

}