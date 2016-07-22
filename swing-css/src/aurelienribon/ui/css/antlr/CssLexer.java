// $ANTLR 3.4 D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g 2012-03-25 19:32:26

	package aurelienribon.ui.css.antlr;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class CssLexer extends Lexer {
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

    	@Override
    	public void emitErrorMessage(String msg) {
    		throw new RuntimeException(msg);
    	}


    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public CssLexer() {} 
    public CssLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public CssLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g"; }

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:12:7: ( '>' )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:12:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "NAMESTART"
    public final void mNAMESTART() throws RecognitionException {
        try {
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:152:2: ( '_' | 'a' .. 'z' | 'A' .. 'Z' )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NAMESTART"

    // $ANTLR start "NAMECHAR"
    public final void mNAMECHAR() throws RecognitionException {
        try {
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:158:2: ( '_' | 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:
            {
            if ( input.LA(1)=='-'||(input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NAMECHAR"

    // $ANTLR start "HASH"
    public final void mHASH() throws RecognitionException {
        try {
            int _type = HASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:165:6: ( '#' ( NAMECHAR )+ )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:165:8: '#' ( NAMECHAR )+
            {
            match('#'); 

            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:165:12: ( NAMECHAR )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='-'||(LA1_0 >= '0' && LA1_0 <= '9')||(LA1_0 >= 'A' && LA1_0 <= 'Z')||LA1_0=='_'||(LA1_0 >= 'a' && LA1_0 <= 'z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:
            	    {
            	    if ( input.LA(1)=='-'||(input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "HASH"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:166:6: ( '.' )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:166:8: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "STAR"
    public final void mSTAR() throws RecognitionException {
        try {
            int _type = STAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:167:6: ( '*' )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:167:8: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STAR"

    // $ANTLR start "COLON"
    public final void mCOLON() throws RecognitionException {
        try {
            int _type = COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:168:7: ( ':' )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:168:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COLON"

    // $ANTLR start "SEMI"
    public final void mSEMI() throws RecognitionException {
        try {
            int _type = SEMI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:169:6: ( ';' )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:169:8: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SEMI"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:170:7: ( ',' )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:170:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "LBRACKET"
    public final void mLBRACKET() throws RecognitionException {
        try {
            int _type = LBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:171:9: ( '[' )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:171:11: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LBRACKET"

    // $ANTLR start "RBRACKET"
    public final void mRBRACKET() throws RecognitionException {
        try {
            int _type = RBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:172:9: ( ']' )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:172:11: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RBRACKET"

    // $ANTLR start "EQUAL"
    public final void mEQUAL() throws RecognitionException {
        try {
            int _type = EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:173:7: ( '=' )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:173:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EQUAL"

    // $ANTLR start "LBRACE"
    public final void mLBRACE() throws RecognitionException {
        try {
            int _type = LBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:174:8: ( '(' )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:174:10: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LBRACE"

    // $ANTLR start "RBRACE"
    public final void mRBRACE() throws RecognitionException {
        try {
            int _type = RBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:175:8: ( ')' )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:175:10: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RBRACE"

    // $ANTLR start "LCURLY"
    public final void mLCURLY() throws RecognitionException {
        try {
            int _type = LCURLY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:176:8: ( '{' )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:176:10: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LCURLY"

    // $ANTLR start "RCURLY"
    public final void mRCURLY() throws RecognitionException {
        try {
            int _type = RCURLY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:177:8: ( '}' )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:177:10: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RCURLY"

    // $ANTLR start "TRUE"
    public final void mTRUE() throws RecognitionException {
        try {
            int _type = TRUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:178:6: ( 'true' )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:178:8: 'true'
            {
            match("true"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TRUE"

    // $ANTLR start "FALSE"
    public final void mFALSE() throws RecognitionException {
        try {
            int _type = FALSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:179:7: ( 'false' )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:179:9: 'false'
            {
            match("false"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FALSE"

    // $ANTLR start "NULL"
    public final void mNULL() throws RecognitionException {
        try {
            int _type = NULL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:180:6: ( 'null' )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:180:8: 'null'
            {
            match("null"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NULL"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:183:2: ( '\"' (~ ( '\"' ) )* '\"' )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:183:4: '\"' (~ ( '\"' ) )* '\"'
            {
            match('\"'); 

            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:183:8: (~ ( '\"' ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0 >= '\u0000' && LA2_0 <= '!')||(LA2_0 >= '#' && LA2_0 <= '\uFFFF')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '\uFFFF') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "IDENT"
    public final void mIDENT() throws RecognitionException {
        try {
            int _type = IDENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:187:2: ( ( '-' )? NAMESTART ( NAMECHAR )* )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:187:4: ( '-' )? NAMESTART ( NAMECHAR )*
            {
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:187:4: ( '-' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='-') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:187:4: '-'
                    {
                    match('-'); 

                    }
                    break;

            }


            mNAMESTART(); 


            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:187:19: ( NAMECHAR )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0=='-'||(LA4_0 >= '0' && LA4_0 <= '9')||(LA4_0 >= 'A' && LA4_0 <= 'Z')||LA4_0=='_'||(LA4_0 >= 'a' && LA4_0 <= 'z')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:
            	    {
            	    if ( input.LA(1)=='-'||(input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "IDENT"

    // $ANTLR start "NUMBER"
    public final void mNUMBER() throws RecognitionException {
        try {
            int _type = NUMBER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:191:2: ( ( '0' .. '9' )+ ( '.' ( '0' .. '9' )+ )? | '.' ( '0' .. '9' )+ )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( ((LA9_0 >= '0' && LA9_0 <= '9')) ) {
                alt9=1;
            }
            else if ( (LA9_0=='.') ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;

            }
            switch (alt9) {
                case 1 :
                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:191:4: ( '0' .. '9' )+ ( '.' ( '0' .. '9' )+ )?
                    {
                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:191:4: ( '0' .. '9' )+
                    int cnt5=0;
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( ((LA5_0 >= '0' && LA5_0 <= '9')) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt5 >= 1 ) break loop5;
                                EarlyExitException eee =
                                    new EarlyExitException(5, input);
                                throw eee;
                        }
                        cnt5++;
                    } while (true);


                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:191:14: ( '.' ( '0' .. '9' )+ )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0=='.') ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:191:15: '.' ( '0' .. '9' )+
                            {
                            match('.'); 

                            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:191:19: ( '0' .. '9' )+
                            int cnt6=0;
                            loop6:
                            do {
                                int alt6=2;
                                int LA6_0 = input.LA(1);

                                if ( ((LA6_0 >= '0' && LA6_0 <= '9')) ) {
                                    alt6=1;
                                }


                                switch (alt6) {
                            	case 1 :
                            	    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:
                            	    {
                            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                            	        input.consume();
                            	    }
                            	    else {
                            	        MismatchedSetException mse = new MismatchedSetException(null,input);
                            	        recover(mse);
                            	        throw mse;
                            	    }


                            	    }
                            	    break;

                            	default :
                            	    if ( cnt6 >= 1 ) break loop6;
                                        EarlyExitException eee =
                                            new EarlyExitException(6, input);
                                        throw eee;
                                }
                                cnt6++;
                            } while (true);


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:191:33: '.' ( '0' .. '9' )+
                    {
                    match('.'); 

                    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:191:37: ( '0' .. '9' )+
                    int cnt8=0;
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( ((LA8_0 >= '0' && LA8_0 <= '9')) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt8 >= 1 ) break loop8;
                                EarlyExitException eee =
                                    new EarlyExitException(8, input);
                                throw eee;
                        }
                        cnt8++;
                    } while (true);


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NUMBER"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:195:2: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:195:4: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:195:4: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0 >= '\t' && LA10_0 <= '\n')||LA10_0=='\r'||LA10_0==' ') ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:
            	    {
            	    if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);


            _channel = HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:199:2: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:199:4: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 



            // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:199:9: ( options {greedy=false; } : . )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0=='*') ) {
                    int LA11_1 = input.LA(2);

                    if ( (LA11_1=='/') ) {
                        alt11=2;
                    }
                    else if ( ((LA11_1 >= '\u0000' && LA11_1 <= '.')||(LA11_1 >= '0' && LA11_1 <= '\uFFFF')) ) {
                        alt11=1;
                    }


                }
                else if ( ((LA11_0 >= '\u0000' && LA11_0 <= ')')||(LA11_0 >= '+' && LA11_0 <= '\uFFFF')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:199:37: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


            match("*/"); 



            skip();

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMENT"

    public void mTokens() throws RecognitionException {
        // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:8: ( T__27 | HASH | DOT | STAR | COLON | SEMI | COMMA | LBRACKET | RBRACKET | EQUAL | LBRACE | RBRACE | LCURLY | RCURLY | TRUE | FALSE | NULL | STRING | IDENT | NUMBER | WS | COMMENT )
        int alt12=22;
        switch ( input.LA(1) ) {
        case '>':
            {
            alt12=1;
            }
            break;
        case '#':
            {
            alt12=2;
            }
            break;
        case '.':
            {
            int LA12_3 = input.LA(2);

            if ( ((LA12_3 >= '0' && LA12_3 <= '9')) ) {
                alt12=20;
            }
            else {
                alt12=3;
            }
            }
            break;
        case '*':
            {
            alt12=4;
            }
            break;
        case ':':
            {
            alt12=5;
            }
            break;
        case ';':
            {
            alt12=6;
            }
            break;
        case ',':
            {
            alt12=7;
            }
            break;
        case '[':
            {
            alt12=8;
            }
            break;
        case ']':
            {
            alt12=9;
            }
            break;
        case '=':
            {
            alt12=10;
            }
            break;
        case '(':
            {
            alt12=11;
            }
            break;
        case ')':
            {
            alt12=12;
            }
            break;
        case '{':
            {
            alt12=13;
            }
            break;
        case '}':
            {
            alt12=14;
            }
            break;
        case 't':
            {
            int LA12_15 = input.LA(2);

            if ( (LA12_15=='r') ) {
                int LA12_24 = input.LA(3);

                if ( (LA12_24=='u') ) {
                    int LA12_27 = input.LA(4);

                    if ( (LA12_27=='e') ) {
                        int LA12_30 = input.LA(5);

                        if ( (LA12_30=='-'||(LA12_30 >= '0' && LA12_30 <= '9')||(LA12_30 >= 'A' && LA12_30 <= 'Z')||LA12_30=='_'||(LA12_30 >= 'a' && LA12_30 <= 'z')) ) {
                            alt12=19;
                        }
                        else {
                            alt12=15;
                        }
                    }
                    else {
                        alt12=19;
                    }
                }
                else {
                    alt12=19;
                }
            }
            else {
                alt12=19;
            }
            }
            break;
        case 'f':
            {
            int LA12_16 = input.LA(2);

            if ( (LA12_16=='a') ) {
                int LA12_25 = input.LA(3);

                if ( (LA12_25=='l') ) {
                    int LA12_28 = input.LA(4);

                    if ( (LA12_28=='s') ) {
                        int LA12_31 = input.LA(5);

                        if ( (LA12_31=='e') ) {
                            int LA12_34 = input.LA(6);

                            if ( (LA12_34=='-'||(LA12_34 >= '0' && LA12_34 <= '9')||(LA12_34 >= 'A' && LA12_34 <= 'Z')||LA12_34=='_'||(LA12_34 >= 'a' && LA12_34 <= 'z')) ) {
                                alt12=19;
                            }
                            else {
                                alt12=16;
                            }
                        }
                        else {
                            alt12=19;
                        }
                    }
                    else {
                        alt12=19;
                    }
                }
                else {
                    alt12=19;
                }
            }
            else {
                alt12=19;
            }
            }
            break;
        case 'n':
            {
            int LA12_17 = input.LA(2);

            if ( (LA12_17=='u') ) {
                int LA12_26 = input.LA(3);

                if ( (LA12_26=='l') ) {
                    int LA12_29 = input.LA(4);

                    if ( (LA12_29=='l') ) {
                        int LA12_32 = input.LA(5);

                        if ( (LA12_32=='-'||(LA12_32 >= '0' && LA12_32 <= '9')||(LA12_32 >= 'A' && LA12_32 <= 'Z')||LA12_32=='_'||(LA12_32 >= 'a' && LA12_32 <= 'z')) ) {
                            alt12=19;
                        }
                        else {
                            alt12=17;
                        }
                    }
                    else {
                        alt12=19;
                    }
                }
                else {
                    alt12=19;
                }
            }
            else {
                alt12=19;
            }
            }
            break;
        case '\"':
            {
            alt12=18;
            }
            break;
        case '-':
        case 'A':
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'F':
        case 'G':
        case 'H':
        case 'I':
        case 'J':
        case 'K':
        case 'L':
        case 'M':
        case 'N':
        case 'O':
        case 'P':
        case 'Q':
        case 'R':
        case 'S':
        case 'T':
        case 'U':
        case 'V':
        case 'W':
        case 'X':
        case 'Y':
        case 'Z':
        case '_':
        case 'a':
        case 'b':
        case 'c':
        case 'd':
        case 'e':
        case 'g':
        case 'h':
        case 'i':
        case 'j':
        case 'k':
        case 'l':
        case 'm':
        case 'o':
        case 'p':
        case 'q':
        case 'r':
        case 's':
        case 'u':
        case 'v':
        case 'w':
        case 'x':
        case 'y':
        case 'z':
            {
            alt12=19;
            }
            break;
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            {
            alt12=20;
            }
            break;
        case '\t':
        case '\n':
        case '\r':
        case ' ':
            {
            alt12=21;
            }
            break;
        case '/':
            {
            alt12=22;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 12, 0, input);

            throw nvae;

        }

        switch (alt12) {
            case 1 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:10: T__27
                {
                mT__27(); 


                }
                break;
            case 2 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:16: HASH
                {
                mHASH(); 


                }
                break;
            case 3 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:21: DOT
                {
                mDOT(); 


                }
                break;
            case 4 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:25: STAR
                {
                mSTAR(); 


                }
                break;
            case 5 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:30: COLON
                {
                mCOLON(); 


                }
                break;
            case 6 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:36: SEMI
                {
                mSEMI(); 


                }
                break;
            case 7 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:41: COMMA
                {
                mCOMMA(); 


                }
                break;
            case 8 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:47: LBRACKET
                {
                mLBRACKET(); 


                }
                break;
            case 9 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:56: RBRACKET
                {
                mRBRACKET(); 


                }
                break;
            case 10 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:65: EQUAL
                {
                mEQUAL(); 


                }
                break;
            case 11 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:71: LBRACE
                {
                mLBRACE(); 


                }
                break;
            case 12 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:78: RBRACE
                {
                mRBRACE(); 


                }
                break;
            case 13 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:85: LCURLY
                {
                mLCURLY(); 


                }
                break;
            case 14 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:92: RCURLY
                {
                mRCURLY(); 


                }
                break;
            case 15 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:99: TRUE
                {
                mTRUE(); 


                }
                break;
            case 16 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:104: FALSE
                {
                mFALSE(); 


                }
                break;
            case 17 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:110: NULL
                {
                mNULL(); 


                }
                break;
            case 18 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:115: STRING
                {
                mSTRING(); 


                }
                break;
            case 19 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:122: IDENT
                {
                mIDENT(); 


                }
                break;
            case 20 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:128: NUMBER
                {
                mNUMBER(); 


                }
                break;
            case 21 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:135: WS
                {
                mWS(); 


                }
                break;
            case 22 :
                // D:\\Dev\\Java\\css-engine\\api\\antlr\\Css.g:1:138: COMMENT
                {
                mCOMMENT(); 


                }
                break;

        }

    }


 

}