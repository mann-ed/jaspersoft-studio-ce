package com.jaspersoft.studio.data.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import com.jaspersoft.studio.data.services.SqlGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalSqlParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "MINUTE_MICROSECOND", "SECOND_MICROSECOND", "HOUR_MICROSECOND", "DAY_MICROSECOND", "MINUTE_SECOND", "STRAIGHT_JOIN", "HOUR_MINUTE", "HOUR_SECOND", "MICROSECOND", "DAY_MINUTE", "DAY_SECOND", "YEAR_MONTH", "BETWEEN_4", "KW_FOLLOWING", "INTERSECT", "PARTITION", "PRECEDING", "UNBOUNDED", "BETWEEN_2", "NOTEQUAL", "BETWEEN_3", "GREATER_1", "DAY_HOUR", "DISTINCT", "SIBLINGS", "BETWEEN_1", "GREATER", "BETWEEN", "CURRENT", "EXCLUDE", "EXTRACT", "INCLUDE", "NATURAL", "PERCENT", "QUARTER", "UNPIVOT", "EXCEPT", "EXISTS", "HAVING", "MINUTE", "OFFSET", "SECOND", "SELECT", "VALUES", "EQUAL", "LESS_1", "NOTIN", "CAST", "CROSS", "FETCH", "FIRST", "GROUP", "INNER", "LIMIT", "MINUS", "MONTH", "NULLS", "ORDER", "OUTER", "PIVOT", "RANGE", "RIGHT", "UNION", "USING", "WHERE", "LESS", "CASE", "DESC", "ELSE", "FROM", "FULL", "HOUR", "JOIN", "LAST", "LEFT", "LIKE", "NOT_1", "NULL", "ONLY", "OVER", "ROWS", "SOME", "THEN", "TIES", "WEEK", "WHEN", "WITH", "YEAR", "LeftParenthesisPlusSignRightParenthesis", "ALL", "AND", "ANY", "ASC", "DAY", "END", "FOR", "NOT", "ROW", "TOP", "XML", "IN_1", "ExclamationMarkEqualsSign", "X", "LessThanSignEqualsSign", "LessThanSignGreaterThanSign", "GreaterThanSignEqualsSign", "AS", "BY", "IN", "IS", "ON", "OR", "CircumflexAccentEqualsSign", "VerticalLineVerticalLine", "LeftParenthesis", "RightParenthesis", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "LessThanSign", "EqualsSign", "GreaterThanSign", "VerticalLine", "RightCurlyBracket", "RULE_JRPARAM", "RULE_JRNPARAM", "RULE_STAR", "RULE_UNSIGNED", "RULE_INT", "RULE_SIGNED_DOUBLE", "RULE_DATE", "RULE_TIME", "RULE_TIMESTAMP", "RULE_STRING_CORE", "RULE_STRING_", "RULE_STRING", "RULE_DBNAME", "RULE_ID", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int LessThanSignGreaterThanSign=108;
    public static final int MONTH=59;
    public static final int JOIN=76;
    public static final int BETWEEN=31;
    public static final int LessThanSign=125;
    public static final int MINUS=58;
    public static final int LeftParenthesisPlusSignRightParenthesis=92;
    public static final int WHEN=89;
    public static final int QUARTER=38;
    public static final int LeftParenthesis=118;
    public static final int YEAR=91;
    public static final int ELSE=72;
    public static final int RULE_TIME=137;
    public static final int HOUR_MINUTE=10;
    public static final int PARTITION=19;
    public static final int INCLUDE=35;
    public static final int DAY_MINUTE=13;
    public static final int INNER=56;
    public static final int CAST=51;
    public static final int GreaterThanSign=127;
    public static final int LEFT=78;
    public static final int RULE_ID=143;
    public static final int IN=112;
    public static final int DISTINCT=27;
    public static final int IS=113;
    public static final int WHERE=68;
    public static final int CASE=70;
    public static final int GreaterThanSignEqualsSign=109;
    public static final int AS=110;
    public static final int RULE_DATE=136;
    public static final int TOP=102;
    public static final int VerticalLine=128;
    public static final int PlusSign=120;
    public static final int RULE_INT=134;
    public static final int RULE_ML_COMMENT=144;
    public static final int THEN=86;
    public static final int UNPIVOT=39;
    public static final int RULE_JRPARAM=130;
    public static final int MICROSECOND=12;
    public static final int VerticalLineVerticalLine=117;
    public static final int DAY_HOUR=26;
    public static final int RULE_DBNAME=142;
    public static final int GROUP=55;
    public static final int ORDER=61;
    public static final int ASC=96;
    public static final int Comma=121;
    public static final int HyphenMinus=122;
    public static final int STRAIGHT_JOIN=9;
    public static final int BY=111;
    public static final int X=106;
    public static final int OFFSET=44;
    public static final int RIGHT=65;
    public static final int VALUES=47;
    public static final int LessThanSignEqualsSign=107;
    public static final int Solidus=124;
    public static final int RightCurlyBracket=129;
    public static final int RULE_SIGNED_DOUBLE=135;
    public static final int FETCH=53;
    public static final int FullStop=123;
    public static final int RULE_UNSIGNED=133;
    public static final int SIBLINGS=28;
    public static final int GREATER=30;
    public static final int NOTIN=50;
    public static final int SECOND_MICROSECOND=5;
    public static final int FIRST=54;
    public static final int RULE_STRING_=140;
    public static final int SELECT=46;
    public static final int PRECEDING=20;
    public static final int RULE_JRNPARAM=131;
    public static final int PERCENT=37;
    public static final int ExclamationMarkEqualsSign=105;
    public static final int UNION=66;
    public static final int DAY=97;
    public static final int ALL=93;
    public static final int ONLY=82;
    public static final int FROM=73;
    public static final int DESC=71;
    public static final int MINUTE_MICROSECOND=4;
    public static final int UNBOUNDED=21;
    public static final int KW_FOLLOWING=17;
    public static final int MINUTE=43;
    public static final int RULE_STAR=132;
    public static final int HOUR_MICROSECOND=6;
    public static final int EXTRACT=34;
    public static final int NULL=81;
    public static final int DAY_MICROSECOND=7;
    public static final int LESS_1=49;
    public static final int FOR=99;
    public static final int RightParenthesis=119;
    public static final int PIVOT=63;
    public static final int EXCEPT=40;
    public static final int CURRENT=32;
    public static final int FULL=74;
    public static final int NOTEQUAL=23;
    public static final int USING=67;
    public static final int NOT=100;
    public static final int LIKE=79;
    public static final int LAST=77;
    public static final int IN_1=104;
    public static final int EXCLUDE=33;
    public static final int AND=94;
    public static final int CircumflexAccentEqualsSign=116;
    public static final int MINUTE_SECOND=8;
    public static final int YEAR_MONTH=15;
    public static final int LESS=69;
    public static final int END=98;
    public static final int ROW=101;
    public static final int HAVING=42;
    public static final int DAY_SECOND=14;
    public static final int RANGE=64;
    public static final int TIES=87;
    public static final int HOUR=75;
    public static final int LIMIT=57;
    public static final int RULE_STRING=141;
    public static final int ANY=95;
    public static final int RULE_SL_COMMENT=145;
    public static final int NATURAL=36;
    public static final int EqualsSign=126;
    public static final int SOME=85;
    public static final int NOT_1=80;
    public static final int BETWEEN_2=22;
    public static final int GREATER_1=25;
    public static final int BETWEEN_1=29;
    public static final int OUTER=62;
    public static final int WEEK=88;
    public static final int EOF=-1;
    public static final int BETWEEN_4=16;
    public static final int NULLS=60;
    public static final int BETWEEN_3=24;
    public static final int ON=114;
    public static final int OR=115;
    public static final int EXISTS=41;
    public static final int RULE_WS=146;
    public static final int EQUAL=48;
    public static final int RULE_ANY_OTHER=147;
    public static final int INTERSECT=18;
    public static final int WITH=90;
    public static final int OVER=83;
    public static final int CROSS=52;
    public static final int XML=103;
    public static final int SECOND=45;
    public static final int RULE_STRING_CORE=139;
    public static final int HOUR_SECOND=11;
    public static final int RULE_TIMESTAMP=138;
    public static final int ROWS=84;

    // delegates
    // delegators


        public InternalSqlParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalSqlParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalSqlParser.tokenNames; }
    public String getGrammarFileName() { return "InternalSqlParser.g"; }



    /*
      This grammar contains a lot of empty actions to work around a bug in ANTLR.
      Otherwise the ANTLR tool will create synpreds that cannot be compiled in some rare cases.
    */
     

    	private SqlGrammarAccess grammarAccess;
    	 	
    	public InternalSqlParser(TokenStream input, SqlGrammarAccess grammarAccess) {
    		this(input);
    		this.grammarAccess = grammarAccess;
    		registerRules(grammarAccess.getGrammar());
    	}
    	
    	@Override
    	protected String getFirstRuleName() {
    		return "Model";	
    	} 
    	   	   	
    	@Override
    	protected SqlGrammarAccess getGrammarAccess() {
    		return grammarAccess;
    	}



    // $ANTLR start "entryRuleModel"
    // InternalSqlParser.g:68:1: entryRuleModel returns [EObject current=null] : iv_ruleModel= ruleModel EOF ;
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModel = null;


        try {
            // InternalSqlParser.g:69:2: (iv_ruleModel= ruleModel EOF )
            // InternalSqlParser.g:70:2: iv_ruleModel= ruleModel EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getModelRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleModel=ruleModel();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleModel; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // InternalSqlParser.g:77:1: ruleModel returns [EObject current=null] : ( (this_JRNPARAM_0= RULE_JRNPARAM )? ( (lv_wq_1_0= ruleWithQuery ) )? ( (lv_query_2_0= ruleSelectQuery ) ) ) ;
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;

        Token this_JRNPARAM_0=null;
        EObject lv_wq_1_0 = null;

        EObject lv_query_2_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:80:28: ( ( (this_JRNPARAM_0= RULE_JRNPARAM )? ( (lv_wq_1_0= ruleWithQuery ) )? ( (lv_query_2_0= ruleSelectQuery ) ) ) )
            // InternalSqlParser.g:81:1: ( (this_JRNPARAM_0= RULE_JRNPARAM )? ( (lv_wq_1_0= ruleWithQuery ) )? ( (lv_query_2_0= ruleSelectQuery ) ) )
            {
            // InternalSqlParser.g:81:1: ( (this_JRNPARAM_0= RULE_JRNPARAM )? ( (lv_wq_1_0= ruleWithQuery ) )? ( (lv_query_2_0= ruleSelectQuery ) ) )
            // InternalSqlParser.g:81:2: (this_JRNPARAM_0= RULE_JRNPARAM )? ( (lv_wq_1_0= ruleWithQuery ) )? ( (lv_query_2_0= ruleSelectQuery ) )
            {
            // InternalSqlParser.g:81:2: (this_JRNPARAM_0= RULE_JRNPARAM )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==RULE_JRNPARAM) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // InternalSqlParser.g:81:3: this_JRNPARAM_0= RULE_JRNPARAM
                    {
                    this_JRNPARAM_0=(Token)match(input,RULE_JRNPARAM,FOLLOW_3); if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                          newLeafNode(this_JRNPARAM_0, grammarAccess.getModelAccess().getJRNPARAMTerminalRuleCall_0()); 
                          
                    }

                    }
                    break;

            }

            // InternalSqlParser.g:85:3: ( (lv_wq_1_0= ruleWithQuery ) )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==WITH) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // InternalSqlParser.g:86:1: (lv_wq_1_0= ruleWithQuery )
                    {
                    // InternalSqlParser.g:86:1: (lv_wq_1_0= ruleWithQuery )
                    // InternalSqlParser.g:87:3: lv_wq_1_0= ruleWithQuery
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getModelAccess().getWqWithQueryParserRuleCall_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_3);
                    lv_wq_1_0=ruleWithQuery();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getModelRule());
                      	        }
                             		set(
                             			current, 
                             			"wq",
                              		lv_wq_1_0, 
                              		"com.jaspersoft.studio.data.Sql.WithQuery");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }
                    break;

            }

            // InternalSqlParser.g:103:3: ( (lv_query_2_0= ruleSelectQuery ) )
            // InternalSqlParser.g:104:1: (lv_query_2_0= ruleSelectQuery )
            {
            // InternalSqlParser.g:104:1: (lv_query_2_0= ruleSelectQuery )
            // InternalSqlParser.g:105:3: lv_query_2_0= ruleSelectQuery
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getModelAccess().getQuerySelectQueryParserRuleCall_2_0()); 
              	    
            }
            pushFollow(FOLLOW_2);
            lv_query_2_0=ruleSelectQuery();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getModelRule());
              	        }
                     		set(
                     			current, 
                     			"query",
                      		lv_query_2_0, 
                      		"com.jaspersoft.studio.data.Sql.SelectQuery");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleWithQuery"
    // InternalSqlParser.g:129:1: entryRuleWithQuery returns [EObject current=null] : iv_ruleWithQuery= ruleWithQuery EOF ;
    public final EObject entryRuleWithQuery() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWithQuery = null;


        try {
            // InternalSqlParser.g:130:2: (iv_ruleWithQuery= ruleWithQuery EOF )
            // InternalSqlParser.g:131:2: iv_ruleWithQuery= ruleWithQuery EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getWithQueryRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleWithQuery=ruleWithQuery();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleWithQuery; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleWithQuery"


    // $ANTLR start "ruleWithQuery"
    // InternalSqlParser.g:138:1: ruleWithQuery returns [EObject current=null] : ( ( (lv_w_0_0= WITH ) ) ( (lv_wname_1_0= ruleDbObjectName ) ) ( (lv_withCols_2_0= ruleWithColumns ) )? otherlv_3= AS otherlv_4= LeftParenthesis ( (lv_query_5_0= ruleSelectQuery ) ) otherlv_6= RightParenthesis (otherlv_7= Comma ( (lv_additionalWname_8_0= ruleDbObjectName ) ) ( (lv_additionalWithCols_9_0= ruleWithColumns ) )? otherlv_10= AS otherlv_11= LeftParenthesis ( (lv_additionalQueries_12_0= ruleSelectQuery ) ) otherlv_13= RightParenthesis )* ) ;
    public final EObject ruleWithQuery() throws RecognitionException {
        EObject current = null;

        Token lv_w_0_0=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        EObject lv_wname_1_0 = null;

        EObject lv_withCols_2_0 = null;

        EObject lv_query_5_0 = null;

        EObject lv_additionalWname_8_0 = null;

        EObject lv_additionalWithCols_9_0 = null;

        EObject lv_additionalQueries_12_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:141:28: ( ( ( (lv_w_0_0= WITH ) ) ( (lv_wname_1_0= ruleDbObjectName ) ) ( (lv_withCols_2_0= ruleWithColumns ) )? otherlv_3= AS otherlv_4= LeftParenthesis ( (lv_query_5_0= ruleSelectQuery ) ) otherlv_6= RightParenthesis (otherlv_7= Comma ( (lv_additionalWname_8_0= ruleDbObjectName ) ) ( (lv_additionalWithCols_9_0= ruleWithColumns ) )? otherlv_10= AS otherlv_11= LeftParenthesis ( (lv_additionalQueries_12_0= ruleSelectQuery ) ) otherlv_13= RightParenthesis )* ) )
            // InternalSqlParser.g:142:1: ( ( (lv_w_0_0= WITH ) ) ( (lv_wname_1_0= ruleDbObjectName ) ) ( (lv_withCols_2_0= ruleWithColumns ) )? otherlv_3= AS otherlv_4= LeftParenthesis ( (lv_query_5_0= ruleSelectQuery ) ) otherlv_6= RightParenthesis (otherlv_7= Comma ( (lv_additionalWname_8_0= ruleDbObjectName ) ) ( (lv_additionalWithCols_9_0= ruleWithColumns ) )? otherlv_10= AS otherlv_11= LeftParenthesis ( (lv_additionalQueries_12_0= ruleSelectQuery ) ) otherlv_13= RightParenthesis )* )
            {
            // InternalSqlParser.g:142:1: ( ( (lv_w_0_0= WITH ) ) ( (lv_wname_1_0= ruleDbObjectName ) ) ( (lv_withCols_2_0= ruleWithColumns ) )? otherlv_3= AS otherlv_4= LeftParenthesis ( (lv_query_5_0= ruleSelectQuery ) ) otherlv_6= RightParenthesis (otherlv_7= Comma ( (lv_additionalWname_8_0= ruleDbObjectName ) ) ( (lv_additionalWithCols_9_0= ruleWithColumns ) )? otherlv_10= AS otherlv_11= LeftParenthesis ( (lv_additionalQueries_12_0= ruleSelectQuery ) ) otherlv_13= RightParenthesis )* )
            // InternalSqlParser.g:142:2: ( (lv_w_0_0= WITH ) ) ( (lv_wname_1_0= ruleDbObjectName ) ) ( (lv_withCols_2_0= ruleWithColumns ) )? otherlv_3= AS otherlv_4= LeftParenthesis ( (lv_query_5_0= ruleSelectQuery ) ) otherlv_6= RightParenthesis (otherlv_7= Comma ( (lv_additionalWname_8_0= ruleDbObjectName ) ) ( (lv_additionalWithCols_9_0= ruleWithColumns ) )? otherlv_10= AS otherlv_11= LeftParenthesis ( (lv_additionalQueries_12_0= ruleSelectQuery ) ) otherlv_13= RightParenthesis )*
            {
            // InternalSqlParser.g:142:2: ( (lv_w_0_0= WITH ) )
            // InternalSqlParser.g:143:1: (lv_w_0_0= WITH )
            {
            // InternalSqlParser.g:143:1: (lv_w_0_0= WITH )
            // InternalSqlParser.g:144:3: lv_w_0_0= WITH
            {
            lv_w_0_0=(Token)match(input,WITH,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      newLeafNode(lv_w_0_0, grammarAccess.getWithQueryAccess().getWWITHKeyword_0_0());
                  
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getWithQueryRule());
              	        }
                     		setWithLastConsumed(current, "w", lv_w_0_0, "WITH");
              	    
            }

            }


            }

            // InternalSqlParser.g:158:2: ( (lv_wname_1_0= ruleDbObjectName ) )
            // InternalSqlParser.g:159:1: (lv_wname_1_0= ruleDbObjectName )
            {
            // InternalSqlParser.g:159:1: (lv_wname_1_0= ruleDbObjectName )
            // InternalSqlParser.g:160:3: lv_wname_1_0= ruleDbObjectName
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getWithQueryAccess().getWnameDbObjectNameParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_5);
            lv_wname_1_0=ruleDbObjectName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getWithQueryRule());
              	        }
                     		set(
                     			current, 
                     			"wname",
                      		lv_wname_1_0, 
                      		"com.jaspersoft.studio.data.Sql.DbObjectName");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:176:2: ( (lv_withCols_2_0= ruleWithColumns ) )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==LeftParenthesis) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // InternalSqlParser.g:177:1: (lv_withCols_2_0= ruleWithColumns )
                    {
                    // InternalSqlParser.g:177:1: (lv_withCols_2_0= ruleWithColumns )
                    // InternalSqlParser.g:178:3: lv_withCols_2_0= ruleWithColumns
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getWithQueryAccess().getWithColsWithColumnsParserRuleCall_2_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_6);
                    lv_withCols_2_0=ruleWithColumns();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getWithQueryRule());
                      	        }
                             		set(
                             			current, 
                             			"withCols",
                              		lv_withCols_2_0, 
                              		"com.jaspersoft.studio.data.Sql.WithColumns");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }
                    break;

            }

            otherlv_3=(Token)match(input,AS,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getWithQueryAccess().getASKeyword_3());
                  
            }
            otherlv_4=(Token)match(input,LeftParenthesis,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_4, grammarAccess.getWithQueryAccess().getLeftParenthesisKeyword_4());
                  
            }
            // InternalSqlParser.g:204:1: ( (lv_query_5_0= ruleSelectQuery ) )
            // InternalSqlParser.g:205:1: (lv_query_5_0= ruleSelectQuery )
            {
            // InternalSqlParser.g:205:1: (lv_query_5_0= ruleSelectQuery )
            // InternalSqlParser.g:206:3: lv_query_5_0= ruleSelectQuery
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getWithQueryAccess().getQuerySelectQueryParserRuleCall_5_0()); 
              	    
            }
            pushFollow(FOLLOW_8);
            lv_query_5_0=ruleSelectQuery();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getWithQueryRule());
              	        }
                     		set(
                     			current, 
                     			"query",
                      		lv_query_5_0, 
                      		"com.jaspersoft.studio.data.Sql.SelectQuery");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_6=(Token)match(input,RightParenthesis,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_6, grammarAccess.getWithQueryAccess().getRightParenthesisKeyword_6());
                  
            }
            // InternalSqlParser.g:227:1: (otherlv_7= Comma ( (lv_additionalWname_8_0= ruleDbObjectName ) ) ( (lv_additionalWithCols_9_0= ruleWithColumns ) )? otherlv_10= AS otherlv_11= LeftParenthesis ( (lv_additionalQueries_12_0= ruleSelectQuery ) ) otherlv_13= RightParenthesis )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==Comma) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalSqlParser.g:228:2: otherlv_7= Comma ( (lv_additionalWname_8_0= ruleDbObjectName ) ) ( (lv_additionalWithCols_9_0= ruleWithColumns ) )? otherlv_10= AS otherlv_11= LeftParenthesis ( (lv_additionalQueries_12_0= ruleSelectQuery ) ) otherlv_13= RightParenthesis
            	    {
            	    otherlv_7=(Token)match(input,Comma,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	          	newLeafNode(otherlv_7, grammarAccess.getWithQueryAccess().getCommaKeyword_7_0());
            	          
            	    }
            	    // InternalSqlParser.g:232:1: ( (lv_additionalWname_8_0= ruleDbObjectName ) )
            	    // InternalSqlParser.g:233:1: (lv_additionalWname_8_0= ruleDbObjectName )
            	    {
            	    // InternalSqlParser.g:233:1: (lv_additionalWname_8_0= ruleDbObjectName )
            	    // InternalSqlParser.g:234:3: lv_additionalWname_8_0= ruleDbObjectName
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getWithQueryAccess().getAdditionalWnameDbObjectNameParserRuleCall_7_1_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_5);
            	    lv_additionalWname_8_0=ruleDbObjectName();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getWithQueryRule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"additionalWname",
            	              		lv_additionalWname_8_0, 
            	              		"com.jaspersoft.studio.data.Sql.DbObjectName");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }

            	    // InternalSqlParser.g:250:2: ( (lv_additionalWithCols_9_0= ruleWithColumns ) )?
            	    int alt4=2;
            	    int LA4_0 = input.LA(1);

            	    if ( (LA4_0==LeftParenthesis) ) {
            	        alt4=1;
            	    }
            	    switch (alt4) {
            	        case 1 :
            	            // InternalSqlParser.g:251:1: (lv_additionalWithCols_9_0= ruleWithColumns )
            	            {
            	            // InternalSqlParser.g:251:1: (lv_additionalWithCols_9_0= ruleWithColumns )
            	            // InternalSqlParser.g:252:3: lv_additionalWithCols_9_0= ruleWithColumns
            	            {
            	            if ( state.backtracking==0 ) {
            	               
            	              	        newCompositeNode(grammarAccess.getWithQueryAccess().getAdditionalWithColsWithColumnsParserRuleCall_7_2_0()); 
            	              	    
            	            }
            	            pushFollow(FOLLOW_6);
            	            lv_additionalWithCols_9_0=ruleWithColumns();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              	        if (current==null) {
            	              	            current = createModelElementForParent(grammarAccess.getWithQueryRule());
            	              	        }
            	                     		add(
            	                     			current, 
            	                     			"additionalWithCols",
            	                      		lv_additionalWithCols_9_0, 
            	                      		"com.jaspersoft.studio.data.Sql.WithColumns");
            	              	        afterParserOrEnumRuleCall();
            	              	    
            	            }

            	            }


            	            }
            	            break;

            	    }

            	    otherlv_10=(Token)match(input,AS,FOLLOW_7); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	          	newLeafNode(otherlv_10, grammarAccess.getWithQueryAccess().getASKeyword_7_3());
            	          
            	    }
            	    otherlv_11=(Token)match(input,LeftParenthesis,FOLLOW_3); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	          	newLeafNode(otherlv_11, grammarAccess.getWithQueryAccess().getLeftParenthesisKeyword_7_4());
            	          
            	    }
            	    // InternalSqlParser.g:278:1: ( (lv_additionalQueries_12_0= ruleSelectQuery ) )
            	    // InternalSqlParser.g:279:1: (lv_additionalQueries_12_0= ruleSelectQuery )
            	    {
            	    // InternalSqlParser.g:279:1: (lv_additionalQueries_12_0= ruleSelectQuery )
            	    // InternalSqlParser.g:280:3: lv_additionalQueries_12_0= ruleSelectQuery
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getWithQueryAccess().getAdditionalQueriesSelectQueryParserRuleCall_7_5_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_8);
            	    lv_additionalQueries_12_0=ruleSelectQuery();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getWithQueryRule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"additionalQueries",
            	              		lv_additionalQueries_12_0, 
            	              		"com.jaspersoft.studio.data.Sql.SelectQuery");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }

            	    otherlv_13=(Token)match(input,RightParenthesis,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	          	newLeafNode(otherlv_13, grammarAccess.getWithQueryAccess().getRightParenthesisKeyword_7_6());
            	          
            	    }

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleWithQuery"


    // $ANTLR start "entryRuleWithColumns"
    // InternalSqlParser.g:309:1: entryRuleWithColumns returns [EObject current=null] : iv_ruleWithColumns= ruleWithColumns EOF ;
    public final EObject entryRuleWithColumns() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWithColumns = null;


        try {
            // InternalSqlParser.g:310:2: (iv_ruleWithColumns= ruleWithColumns EOF )
            // InternalSqlParser.g:311:2: iv_ruleWithColumns= ruleWithColumns EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getWithColumnsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleWithColumns=ruleWithColumns();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleWithColumns; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleWithColumns"


    // $ANTLR start "ruleWithColumns"
    // InternalSqlParser.g:318:1: ruleWithColumns returns [EObject current=null] : (otherlv_0= LeftParenthesis this_UsingCols_1= ruleUsingCols otherlv_2= RightParenthesis ) ;
    public final EObject ruleWithColumns() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject this_UsingCols_1 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:321:28: ( (otherlv_0= LeftParenthesis this_UsingCols_1= ruleUsingCols otherlv_2= RightParenthesis ) )
            // InternalSqlParser.g:322:1: (otherlv_0= LeftParenthesis this_UsingCols_1= ruleUsingCols otherlv_2= RightParenthesis )
            {
            // InternalSqlParser.g:322:1: (otherlv_0= LeftParenthesis this_UsingCols_1= ruleUsingCols otherlv_2= RightParenthesis )
            // InternalSqlParser.g:323:2: otherlv_0= LeftParenthesis this_UsingCols_1= ruleUsingCols otherlv_2= RightParenthesis
            {
            otherlv_0=(Token)match(input,LeftParenthesis,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getWithColumnsAccess().getLeftParenthesisKeyword_0());
                  
            }
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getWithColumnsAccess().getUsingColsParserRuleCall_1()); 
                  
            }
            pushFollow(FOLLOW_8);
            this_UsingCols_1=ruleUsingCols();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_UsingCols_1;
                      afterParserOrEnumRuleCall();
                  
            }
            otherlv_2=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getWithColumnsAccess().getRightParenthesisKeyword_2());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleWithColumns"


    // $ANTLR start "entryRuleFetchFirst"
    // InternalSqlParser.g:352:1: entryRuleFetchFirst returns [EObject current=null] : iv_ruleFetchFirst= ruleFetchFirst EOF ;
    public final EObject entryRuleFetchFirst() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFetchFirst = null;


        try {
            // InternalSqlParser.g:353:2: (iv_ruleFetchFirst= ruleFetchFirst EOF )
            // InternalSqlParser.g:354:2: iv_ruleFetchFirst= ruleFetchFirst EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFetchFirstRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleFetchFirst=ruleFetchFirst();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFetchFirst; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFetchFirst"


    // $ANTLR start "ruleFetchFirst"
    // InternalSqlParser.g:361:1: ruleFetchFirst returns [EObject current=null] : ( ( (lv_fetchFirst_0_0= ruleUnsignedValue ) ) ( ( (lv_row_1_1= ROW | lv_row_1_2= ROWS ) ) ) otherlv_2= ONLY ) ;
    public final EObject ruleFetchFirst() throws RecognitionException {
        EObject current = null;

        Token lv_row_1_1=null;
        Token lv_row_1_2=null;
        Token otherlv_2=null;
        EObject lv_fetchFirst_0_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:364:28: ( ( ( (lv_fetchFirst_0_0= ruleUnsignedValue ) ) ( ( (lv_row_1_1= ROW | lv_row_1_2= ROWS ) ) ) otherlv_2= ONLY ) )
            // InternalSqlParser.g:365:1: ( ( (lv_fetchFirst_0_0= ruleUnsignedValue ) ) ( ( (lv_row_1_1= ROW | lv_row_1_2= ROWS ) ) ) otherlv_2= ONLY )
            {
            // InternalSqlParser.g:365:1: ( ( (lv_fetchFirst_0_0= ruleUnsignedValue ) ) ( ( (lv_row_1_1= ROW | lv_row_1_2= ROWS ) ) ) otherlv_2= ONLY )
            // InternalSqlParser.g:365:2: ( (lv_fetchFirst_0_0= ruleUnsignedValue ) ) ( ( (lv_row_1_1= ROW | lv_row_1_2= ROWS ) ) ) otherlv_2= ONLY
            {
            // InternalSqlParser.g:365:2: ( (lv_fetchFirst_0_0= ruleUnsignedValue ) )
            // InternalSqlParser.g:366:1: (lv_fetchFirst_0_0= ruleUnsignedValue )
            {
            // InternalSqlParser.g:366:1: (lv_fetchFirst_0_0= ruleUnsignedValue )
            // InternalSqlParser.g:367:3: lv_fetchFirst_0_0= ruleUnsignedValue
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getFetchFirstAccess().getFetchFirstUnsignedValueParserRuleCall_0_0()); 
              	    
            }
            pushFollow(FOLLOW_10);
            lv_fetchFirst_0_0=ruleUnsignedValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getFetchFirstRule());
              	        }
                     		set(
                     			current, 
                     			"fetchFirst",
                      		lv_fetchFirst_0_0, 
                      		"com.jaspersoft.studio.data.Sql.UnsignedValue");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:383:2: ( ( (lv_row_1_1= ROW | lv_row_1_2= ROWS ) ) )
            // InternalSqlParser.g:384:1: ( (lv_row_1_1= ROW | lv_row_1_2= ROWS ) )
            {
            // InternalSqlParser.g:384:1: ( (lv_row_1_1= ROW | lv_row_1_2= ROWS ) )
            // InternalSqlParser.g:385:1: (lv_row_1_1= ROW | lv_row_1_2= ROWS )
            {
            // InternalSqlParser.g:385:1: (lv_row_1_1= ROW | lv_row_1_2= ROWS )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==ROW) ) {
                alt6=1;
            }
            else if ( (LA6_0==ROWS) ) {
                alt6=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // InternalSqlParser.g:386:3: lv_row_1_1= ROW
                    {
                    lv_row_1_1=(Token)match(input,ROW,FOLLOW_11); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_row_1_1, grammarAccess.getFetchFirstAccess().getRowROWKeyword_1_0_0());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getFetchFirstRule());
                      	        }
                             		setWithLastConsumed(current, "row", lv_row_1_1, null);
                      	    
                    }

                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:399:8: lv_row_1_2= ROWS
                    {
                    lv_row_1_2=(Token)match(input,ROWS,FOLLOW_11); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_row_1_2, grammarAccess.getFetchFirstAccess().getRowROWSKeyword_1_0_1());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getFetchFirstRule());
                      	        }
                             		setWithLastConsumed(current, "row", lv_row_1_2, null);
                      	    
                    }

                    }
                    break;

            }


            }


            }

            otherlv_2=(Token)match(input,ONLY,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getFetchFirstAccess().getONLYKeyword_2());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFetchFirst"


    // $ANTLR start "entryRuleOffset"
    // InternalSqlParser.g:428:1: entryRuleOffset returns [EObject current=null] : iv_ruleOffset= ruleOffset EOF ;
    public final EObject entryRuleOffset() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOffset = null;


        try {
            // InternalSqlParser.g:429:2: (iv_ruleOffset= ruleOffset EOF )
            // InternalSqlParser.g:430:2: iv_ruleOffset= ruleOffset EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOffsetRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOffset=ruleOffset();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOffset; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOffset"


    // $ANTLR start "ruleOffset"
    // InternalSqlParser.g:437:1: ruleOffset returns [EObject current=null] : ( (lv_offset_0_0= RULE_INT ) ) ;
    public final EObject ruleOffset() throws RecognitionException {
        EObject current = null;

        Token lv_offset_0_0=null;

         enterRule(); 
            
        try {
            // InternalSqlParser.g:440:28: ( ( (lv_offset_0_0= RULE_INT ) ) )
            // InternalSqlParser.g:441:1: ( (lv_offset_0_0= RULE_INT ) )
            {
            // InternalSqlParser.g:441:1: ( (lv_offset_0_0= RULE_INT ) )
            // InternalSqlParser.g:442:1: (lv_offset_0_0= RULE_INT )
            {
            // InternalSqlParser.g:442:1: (lv_offset_0_0= RULE_INT )
            // InternalSqlParser.g:443:3: lv_offset_0_0= RULE_INT
            {
            lv_offset_0_0=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_offset_0_0, grammarAccess.getOffsetAccess().getOffsetINTTerminalRuleCall_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getOffsetRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"offset",
                      		lv_offset_0_0, 
                      		"com.jaspersoft.studio.data.Sql.INT");
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOffset"


    // $ANTLR start "entryRuleLimit"
    // InternalSqlParser.g:467:1: entryRuleLimit returns [EObject current=null] : iv_ruleLimit= ruleLimit EOF ;
    public final EObject entryRuleLimit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLimit = null;


        try {
            // InternalSqlParser.g:468:2: (iv_ruleLimit= ruleLimit EOF )
            // InternalSqlParser.g:469:2: iv_ruleLimit= ruleLimit EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLimitRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleLimit=ruleLimit();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleLimit; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLimit"


    // $ANTLR start "ruleLimit"
    // InternalSqlParser.g:476:1: ruleLimit returns [EObject current=null] : ( ( () otherlv_1= ALL ) | ( ( (lv_l1_2_0= RULE_UNSIGNED ) ) (otherlv_3= Comma ( (lv_l2_4_0= RULE_UNSIGNED ) ) )? ) ) ;
    public final EObject ruleLimit() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_l1_2_0=null;
        Token otherlv_3=null;
        Token lv_l2_4_0=null;

         enterRule(); 
            
        try {
            // InternalSqlParser.g:479:28: ( ( ( () otherlv_1= ALL ) | ( ( (lv_l1_2_0= RULE_UNSIGNED ) ) (otherlv_3= Comma ( (lv_l2_4_0= RULE_UNSIGNED ) ) )? ) ) )
            // InternalSqlParser.g:480:1: ( ( () otherlv_1= ALL ) | ( ( (lv_l1_2_0= RULE_UNSIGNED ) ) (otherlv_3= Comma ( (lv_l2_4_0= RULE_UNSIGNED ) ) )? ) )
            {
            // InternalSqlParser.g:480:1: ( ( () otherlv_1= ALL ) | ( ( (lv_l1_2_0= RULE_UNSIGNED ) ) (otherlv_3= Comma ( (lv_l2_4_0= RULE_UNSIGNED ) ) )? ) )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==ALL) ) {
                alt8=1;
            }
            else if ( (LA8_0==RULE_UNSIGNED) ) {
                alt8=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // InternalSqlParser.g:480:2: ( () otherlv_1= ALL )
                    {
                    // InternalSqlParser.g:480:2: ( () otherlv_1= ALL )
                    // InternalSqlParser.g:480:3: () otherlv_1= ALL
                    {
                    // InternalSqlParser.g:480:3: ()
                    // InternalSqlParser.g:481:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElement(
                                  grammarAccess.getLimitAccess().getLimitAction_0_0(),
                                  current);
                          
                    }

                    }

                    otherlv_1=(Token)match(input,ALL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_1, grammarAccess.getLimitAccess().getALLKeyword_0_1());
                          
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:495:6: ( ( (lv_l1_2_0= RULE_UNSIGNED ) ) (otherlv_3= Comma ( (lv_l2_4_0= RULE_UNSIGNED ) ) )? )
                    {
                    // InternalSqlParser.g:495:6: ( ( (lv_l1_2_0= RULE_UNSIGNED ) ) (otherlv_3= Comma ( (lv_l2_4_0= RULE_UNSIGNED ) ) )? )
                    // InternalSqlParser.g:495:7: ( (lv_l1_2_0= RULE_UNSIGNED ) ) (otherlv_3= Comma ( (lv_l2_4_0= RULE_UNSIGNED ) ) )?
                    {
                    // InternalSqlParser.g:495:7: ( (lv_l1_2_0= RULE_UNSIGNED ) )
                    // InternalSqlParser.g:496:1: (lv_l1_2_0= RULE_UNSIGNED )
                    {
                    // InternalSqlParser.g:496:1: (lv_l1_2_0= RULE_UNSIGNED )
                    // InternalSqlParser.g:497:3: lv_l1_2_0= RULE_UNSIGNED
                    {
                    lv_l1_2_0=(Token)match(input,RULE_UNSIGNED,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(lv_l1_2_0, grammarAccess.getLimitAccess().getL1UNSIGNEDTerminalRuleCall_1_0_0()); 
                      		
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getLimitRule());
                      	        }
                             		setWithLastConsumed(
                             			current, 
                             			"l1",
                              		lv_l1_2_0, 
                              		"com.jaspersoft.studio.data.Sql.UNSIGNED");
                      	    
                    }

                    }


                    }

                    // InternalSqlParser.g:513:2: (otherlv_3= Comma ( (lv_l2_4_0= RULE_UNSIGNED ) ) )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==Comma) ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // InternalSqlParser.g:514:2: otherlv_3= Comma ( (lv_l2_4_0= RULE_UNSIGNED ) )
                            {
                            otherlv_3=(Token)match(input,Comma,FOLLOW_12); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                  	newLeafNode(otherlv_3, grammarAccess.getLimitAccess().getCommaKeyword_1_1_0());
                                  
                            }
                            // InternalSqlParser.g:518:1: ( (lv_l2_4_0= RULE_UNSIGNED ) )
                            // InternalSqlParser.g:519:1: (lv_l2_4_0= RULE_UNSIGNED )
                            {
                            // InternalSqlParser.g:519:1: (lv_l2_4_0= RULE_UNSIGNED )
                            // InternalSqlParser.g:520:3: lv_l2_4_0= RULE_UNSIGNED
                            {
                            lv_l2_4_0=(Token)match(input,RULE_UNSIGNED,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              			newLeafNode(lv_l2_4_0, grammarAccess.getLimitAccess().getL2UNSIGNEDTerminalRuleCall_1_1_1_0()); 
                              		
                            }
                            if ( state.backtracking==0 ) {

                              	        if (current==null) {
                              	            current = createModelElement(grammarAccess.getLimitRule());
                              	        }
                                     		setWithLastConsumed(
                                     			current, 
                                     			"l2",
                                      		lv_l2_4_0, 
                                      		"com.jaspersoft.studio.data.Sql.UNSIGNED");
                              	    
                            }

                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLimit"


    // $ANTLR start "entryRuleSelectQuery"
    // InternalSqlParser.g:544:1: entryRuleSelectQuery returns [EObject current=null] : iv_ruleSelectQuery= ruleSelectQuery EOF ;
    public final EObject entryRuleSelectQuery() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSelectQuery = null;


        try {
            // InternalSqlParser.g:545:2: (iv_ruleSelectQuery= ruleSelectQuery EOF )
            // InternalSqlParser.g:546:2: iv_ruleSelectQuery= ruleSelectQuery EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSelectQueryRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSelectQuery=ruleSelectQuery();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSelectQuery; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSelectQuery"


    // $ANTLR start "ruleSelectQuery"
    // InternalSqlParser.g:553:1: ruleSelectQuery returns [EObject current=null] : (this_Select_0= ruleSelect ( (lv_op_1_0= ruleSelectSubSet ) )* ) ;
    public final EObject ruleSelectQuery() throws RecognitionException {
        EObject current = null;

        EObject this_Select_0 = null;

        EObject lv_op_1_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:556:28: ( (this_Select_0= ruleSelect ( (lv_op_1_0= ruleSelectSubSet ) )* ) )
            // InternalSqlParser.g:557:1: (this_Select_0= ruleSelect ( (lv_op_1_0= ruleSelectSubSet ) )* )
            {
            // InternalSqlParser.g:557:1: (this_Select_0= ruleSelect ( (lv_op_1_0= ruleSelectSubSet ) )* )
            // InternalSqlParser.g:558:2: this_Select_0= ruleSelect ( (lv_op_1_0= ruleSelectSubSet ) )*
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getSelectQueryAccess().getSelectParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_13);
            this_Select_0=ruleSelect();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_Select_0;
                      afterParserOrEnumRuleCall();
                  
            }
            // InternalSqlParser.g:569:1: ( (lv_op_1_0= ruleSelectSubSet ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==INTERSECT||LA9_0==EXCEPT||LA9_0==MINUS||LA9_0==UNION) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalSqlParser.g:570:1: (lv_op_1_0= ruleSelectSubSet )
            	    {
            	    // InternalSqlParser.g:570:1: (lv_op_1_0= ruleSelectSubSet )
            	    // InternalSqlParser.g:571:3: lv_op_1_0= ruleSelectSubSet
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getSelectQueryAccess().getOpSelectSubSetParserRuleCall_1_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_13);
            	    lv_op_1_0=ruleSelectSubSet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getSelectQueryRule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"op",
            	              		lv_op_1_0, 
            	              		"com.jaspersoft.studio.data.Sql.SelectSubSet");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSelectQuery"


    // $ANTLR start "entryRuleSelectSubSet"
    // InternalSqlParser.g:595:1: entryRuleSelectSubSet returns [EObject current=null] : iv_ruleSelectSubSet= ruleSelectSubSet EOF ;
    public final EObject entryRuleSelectSubSet() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSelectSubSet = null;


        try {
            // InternalSqlParser.g:596:2: (iv_ruleSelectSubSet= ruleSelectSubSet EOF )
            // InternalSqlParser.g:597:2: iv_ruleSelectSubSet= ruleSelectSubSet EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSelectSubSetRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSelectSubSet=ruleSelectSubSet();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSelectSubSet; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSelectSubSet"


    // $ANTLR start "ruleSelectSubSet"
    // InternalSqlParser.g:604:1: ruleSelectSubSet returns [EObject current=null] : ( ( ( (lv_op_0_1= UNION | lv_op_0_2= INTERSECT | lv_op_0_3= MINUS | lv_op_0_4= EXCEPT ) ) ) ( (lv_all_1_0= ALL ) )? ( (lv_query_2_0= ruleSelect ) ) ) ;
    public final EObject ruleSelectSubSet() throws RecognitionException {
        EObject current = null;

        Token lv_op_0_1=null;
        Token lv_op_0_2=null;
        Token lv_op_0_3=null;
        Token lv_op_0_4=null;
        Token lv_all_1_0=null;
        EObject lv_query_2_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:607:28: ( ( ( ( (lv_op_0_1= UNION | lv_op_0_2= INTERSECT | lv_op_0_3= MINUS | lv_op_0_4= EXCEPT ) ) ) ( (lv_all_1_0= ALL ) )? ( (lv_query_2_0= ruleSelect ) ) ) )
            // InternalSqlParser.g:608:1: ( ( ( (lv_op_0_1= UNION | lv_op_0_2= INTERSECT | lv_op_0_3= MINUS | lv_op_0_4= EXCEPT ) ) ) ( (lv_all_1_0= ALL ) )? ( (lv_query_2_0= ruleSelect ) ) )
            {
            // InternalSqlParser.g:608:1: ( ( ( (lv_op_0_1= UNION | lv_op_0_2= INTERSECT | lv_op_0_3= MINUS | lv_op_0_4= EXCEPT ) ) ) ( (lv_all_1_0= ALL ) )? ( (lv_query_2_0= ruleSelect ) ) )
            // InternalSqlParser.g:608:2: ( ( (lv_op_0_1= UNION | lv_op_0_2= INTERSECT | lv_op_0_3= MINUS | lv_op_0_4= EXCEPT ) ) ) ( (lv_all_1_0= ALL ) )? ( (lv_query_2_0= ruleSelect ) )
            {
            // InternalSqlParser.g:608:2: ( ( (lv_op_0_1= UNION | lv_op_0_2= INTERSECT | lv_op_0_3= MINUS | lv_op_0_4= EXCEPT ) ) )
            // InternalSqlParser.g:609:1: ( (lv_op_0_1= UNION | lv_op_0_2= INTERSECT | lv_op_0_3= MINUS | lv_op_0_4= EXCEPT ) )
            {
            // InternalSqlParser.g:609:1: ( (lv_op_0_1= UNION | lv_op_0_2= INTERSECT | lv_op_0_3= MINUS | lv_op_0_4= EXCEPT ) )
            // InternalSqlParser.g:610:1: (lv_op_0_1= UNION | lv_op_0_2= INTERSECT | lv_op_0_3= MINUS | lv_op_0_4= EXCEPT )
            {
            // InternalSqlParser.g:610:1: (lv_op_0_1= UNION | lv_op_0_2= INTERSECT | lv_op_0_3= MINUS | lv_op_0_4= EXCEPT )
            int alt10=4;
            switch ( input.LA(1) ) {
            case UNION:
                {
                alt10=1;
                }
                break;
            case INTERSECT:
                {
                alt10=2;
                }
                break;
            case MINUS:
                {
                alt10=3;
                }
                break;
            case EXCEPT:
                {
                alt10=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // InternalSqlParser.g:611:3: lv_op_0_1= UNION
                    {
                    lv_op_0_1=(Token)match(input,UNION,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_op_0_1, grammarAccess.getSelectSubSetAccess().getOpUNIONKeyword_0_0_0());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getSelectSubSetRule());
                      	        }
                             		setWithLastConsumed(current, "op", lv_op_0_1, null);
                      	    
                    }

                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:624:8: lv_op_0_2= INTERSECT
                    {
                    lv_op_0_2=(Token)match(input,INTERSECT,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_op_0_2, grammarAccess.getSelectSubSetAccess().getOpINTERSECTKeyword_0_0_1());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getSelectSubSetRule());
                      	        }
                             		setWithLastConsumed(current, "op", lv_op_0_2, null);
                      	    
                    }

                    }
                    break;
                case 3 :
                    // InternalSqlParser.g:637:8: lv_op_0_3= MINUS
                    {
                    lv_op_0_3=(Token)match(input,MINUS,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_op_0_3, grammarAccess.getSelectSubSetAccess().getOpMINUSKeyword_0_0_2());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getSelectSubSetRule());
                      	        }
                             		setWithLastConsumed(current, "op", lv_op_0_3, null);
                      	    
                    }

                    }
                    break;
                case 4 :
                    // InternalSqlParser.g:650:8: lv_op_0_4= EXCEPT
                    {
                    lv_op_0_4=(Token)match(input,EXCEPT,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_op_0_4, grammarAccess.getSelectSubSetAccess().getOpEXCEPTKeyword_0_0_3());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getSelectSubSetRule());
                      	        }
                             		setWithLastConsumed(current, "op", lv_op_0_4, null);
                      	    
                    }

                    }
                    break;

            }


            }


            }

            // InternalSqlParser.g:666:2: ( (lv_all_1_0= ALL ) )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==ALL) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalSqlParser.g:667:1: (lv_all_1_0= ALL )
                    {
                    // InternalSqlParser.g:667:1: (lv_all_1_0= ALL )
                    // InternalSqlParser.g:668:3: lv_all_1_0= ALL
                    {
                    lv_all_1_0=(Token)match(input,ALL,FOLLOW_3); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_all_1_0, grammarAccess.getSelectSubSetAccess().getAllALLKeyword_1_0());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getSelectSubSetRule());
                      	        }
                             		setWithLastConsumed(current, "all", lv_all_1_0, "ALL");
                      	    
                    }

                    }


                    }
                    break;

            }

            // InternalSqlParser.g:682:3: ( (lv_query_2_0= ruleSelect ) )
            // InternalSqlParser.g:683:1: (lv_query_2_0= ruleSelect )
            {
            // InternalSqlParser.g:683:1: (lv_query_2_0= ruleSelect )
            // InternalSqlParser.g:684:3: lv_query_2_0= ruleSelect
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getSelectSubSetAccess().getQuerySelectParserRuleCall_2_0()); 
              	    
            }
            pushFollow(FOLLOW_2);
            lv_query_2_0=ruleSelect();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getSelectSubSetRule());
              	        }
                     		set(
                     			current, 
                     			"query",
                      		lv_query_2_0, 
                      		"com.jaspersoft.studio.data.Sql.Select");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSelectSubSet"


    // $ANTLR start "entryRuleSelect"
    // InternalSqlParser.g:708:1: entryRuleSelect returns [EObject current=null] : iv_ruleSelect= ruleSelect EOF ;
    public final EObject entryRuleSelect() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSelect = null;


        try {
            // InternalSqlParser.g:709:2: (iv_ruleSelect= ruleSelect EOF )
            // InternalSqlParser.g:710:2: iv_ruleSelect= ruleSelect EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSelectRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSelect=ruleSelect();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSelect; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSelect"


    // $ANTLR start "ruleSelect"
    // InternalSqlParser.g:717:1: ruleSelect returns [EObject current=null] : ( ( (lv_select_0_0= SELECT ) ) (otherlv_1= DISTINCT )? (otherlv_2= TOP (this_INT_3= RULE_INT | this_SIGNED_DOUBLE_4= RULE_SIGNED_DOUBLE ) (otherlv_5= PERCENT )? (otherlv_6= WITH otherlv_7= TIES )? )? ( (lv_cols_8_0= ruleColumns ) ) otherlv_9= FROM ( (lv_tbl_10_0= ruleTables ) ) (otherlv_11= WHERE ( (lv_whereExpression_12_0= ruleFullExpression ) ) )? (otherlv_13= GROUP otherlv_14= BY ( (lv_groupByEntry_15_0= ruleGroupByColumns ) ) )? (otherlv_16= HAVING ( (lv_havingEntry_17_0= ruleFullExpression ) ) )? (otherlv_18= ORDER otherlv_19= BY ( (lv_orderByEntry_20_0= ruleOrderByColumns ) ) )? (otherlv_21= LIMIT ( (lv_lim_22_0= ruleLimit ) ) )? (otherlv_23= OFFSET ( (lv_offset_24_0= ruleOffset ) ) )? (otherlv_25= FETCH otherlv_26= FIRST ( (lv_fetchFirst_27_0= ruleFetchFirst ) ) )? ) ;
    public final EObject ruleSelect() throws RecognitionException {
        EObject current = null;

        Token lv_select_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token this_INT_3=null;
        Token this_SIGNED_DOUBLE_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token otherlv_19=null;
        Token otherlv_21=null;
        Token otherlv_23=null;
        Token otherlv_25=null;
        Token otherlv_26=null;
        EObject lv_cols_8_0 = null;

        EObject lv_tbl_10_0 = null;

        EObject lv_whereExpression_12_0 = null;

        EObject lv_groupByEntry_15_0 = null;

        EObject lv_havingEntry_17_0 = null;

        EObject lv_orderByEntry_20_0 = null;

        EObject lv_lim_22_0 = null;

        EObject lv_offset_24_0 = null;

        EObject lv_fetchFirst_27_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:720:28: ( ( ( (lv_select_0_0= SELECT ) ) (otherlv_1= DISTINCT )? (otherlv_2= TOP (this_INT_3= RULE_INT | this_SIGNED_DOUBLE_4= RULE_SIGNED_DOUBLE ) (otherlv_5= PERCENT )? (otherlv_6= WITH otherlv_7= TIES )? )? ( (lv_cols_8_0= ruleColumns ) ) otherlv_9= FROM ( (lv_tbl_10_0= ruleTables ) ) (otherlv_11= WHERE ( (lv_whereExpression_12_0= ruleFullExpression ) ) )? (otherlv_13= GROUP otherlv_14= BY ( (lv_groupByEntry_15_0= ruleGroupByColumns ) ) )? (otherlv_16= HAVING ( (lv_havingEntry_17_0= ruleFullExpression ) ) )? (otherlv_18= ORDER otherlv_19= BY ( (lv_orderByEntry_20_0= ruleOrderByColumns ) ) )? (otherlv_21= LIMIT ( (lv_lim_22_0= ruleLimit ) ) )? (otherlv_23= OFFSET ( (lv_offset_24_0= ruleOffset ) ) )? (otherlv_25= FETCH otherlv_26= FIRST ( (lv_fetchFirst_27_0= ruleFetchFirst ) ) )? ) )
            // InternalSqlParser.g:721:1: ( ( (lv_select_0_0= SELECT ) ) (otherlv_1= DISTINCT )? (otherlv_2= TOP (this_INT_3= RULE_INT | this_SIGNED_DOUBLE_4= RULE_SIGNED_DOUBLE ) (otherlv_5= PERCENT )? (otherlv_6= WITH otherlv_7= TIES )? )? ( (lv_cols_8_0= ruleColumns ) ) otherlv_9= FROM ( (lv_tbl_10_0= ruleTables ) ) (otherlv_11= WHERE ( (lv_whereExpression_12_0= ruleFullExpression ) ) )? (otherlv_13= GROUP otherlv_14= BY ( (lv_groupByEntry_15_0= ruleGroupByColumns ) ) )? (otherlv_16= HAVING ( (lv_havingEntry_17_0= ruleFullExpression ) ) )? (otherlv_18= ORDER otherlv_19= BY ( (lv_orderByEntry_20_0= ruleOrderByColumns ) ) )? (otherlv_21= LIMIT ( (lv_lim_22_0= ruleLimit ) ) )? (otherlv_23= OFFSET ( (lv_offset_24_0= ruleOffset ) ) )? (otherlv_25= FETCH otherlv_26= FIRST ( (lv_fetchFirst_27_0= ruleFetchFirst ) ) )? )
            {
            // InternalSqlParser.g:721:1: ( ( (lv_select_0_0= SELECT ) ) (otherlv_1= DISTINCT )? (otherlv_2= TOP (this_INT_3= RULE_INT | this_SIGNED_DOUBLE_4= RULE_SIGNED_DOUBLE ) (otherlv_5= PERCENT )? (otherlv_6= WITH otherlv_7= TIES )? )? ( (lv_cols_8_0= ruleColumns ) ) otherlv_9= FROM ( (lv_tbl_10_0= ruleTables ) ) (otherlv_11= WHERE ( (lv_whereExpression_12_0= ruleFullExpression ) ) )? (otherlv_13= GROUP otherlv_14= BY ( (lv_groupByEntry_15_0= ruleGroupByColumns ) ) )? (otherlv_16= HAVING ( (lv_havingEntry_17_0= ruleFullExpression ) ) )? (otherlv_18= ORDER otherlv_19= BY ( (lv_orderByEntry_20_0= ruleOrderByColumns ) ) )? (otherlv_21= LIMIT ( (lv_lim_22_0= ruleLimit ) ) )? (otherlv_23= OFFSET ( (lv_offset_24_0= ruleOffset ) ) )? (otherlv_25= FETCH otherlv_26= FIRST ( (lv_fetchFirst_27_0= ruleFetchFirst ) ) )? )
            // InternalSqlParser.g:721:2: ( (lv_select_0_0= SELECT ) ) (otherlv_1= DISTINCT )? (otherlv_2= TOP (this_INT_3= RULE_INT | this_SIGNED_DOUBLE_4= RULE_SIGNED_DOUBLE ) (otherlv_5= PERCENT )? (otherlv_6= WITH otherlv_7= TIES )? )? ( (lv_cols_8_0= ruleColumns ) ) otherlv_9= FROM ( (lv_tbl_10_0= ruleTables ) ) (otherlv_11= WHERE ( (lv_whereExpression_12_0= ruleFullExpression ) ) )? (otherlv_13= GROUP otherlv_14= BY ( (lv_groupByEntry_15_0= ruleGroupByColumns ) ) )? (otherlv_16= HAVING ( (lv_havingEntry_17_0= ruleFullExpression ) ) )? (otherlv_18= ORDER otherlv_19= BY ( (lv_orderByEntry_20_0= ruleOrderByColumns ) ) )? (otherlv_21= LIMIT ( (lv_lim_22_0= ruleLimit ) ) )? (otherlv_23= OFFSET ( (lv_offset_24_0= ruleOffset ) ) )? (otherlv_25= FETCH otherlv_26= FIRST ( (lv_fetchFirst_27_0= ruleFetchFirst ) ) )?
            {
            // InternalSqlParser.g:721:2: ( (lv_select_0_0= SELECT ) )
            // InternalSqlParser.g:722:1: (lv_select_0_0= SELECT )
            {
            // InternalSqlParser.g:722:1: (lv_select_0_0= SELECT )
            // InternalSqlParser.g:723:3: lv_select_0_0= SELECT
            {
            lv_select_0_0=(Token)match(input,SELECT,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      newLeafNode(lv_select_0_0, grammarAccess.getSelectAccess().getSelectSELECTKeyword_0_0());
                  
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getSelectRule());
              	        }
                     		setWithLastConsumed(current, "select", lv_select_0_0, "SELECT");
              	    
            }

            }


            }

            // InternalSqlParser.g:737:2: (otherlv_1= DISTINCT )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==DISTINCT) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalSqlParser.g:738:2: otherlv_1= DISTINCT
                    {
                    otherlv_1=(Token)match(input,DISTINCT,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_1, grammarAccess.getSelectAccess().getDISTINCTKeyword_1());
                          
                    }

                    }
                    break;

            }

            // InternalSqlParser.g:742:3: (otherlv_2= TOP (this_INT_3= RULE_INT | this_SIGNED_DOUBLE_4= RULE_SIGNED_DOUBLE ) (otherlv_5= PERCENT )? (otherlv_6= WITH otherlv_7= TIES )? )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==TOP) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalSqlParser.g:743:2: otherlv_2= TOP (this_INT_3= RULE_INT | this_SIGNED_DOUBLE_4= RULE_SIGNED_DOUBLE ) (otherlv_5= PERCENT )? (otherlv_6= WITH otherlv_7= TIES )?
                    {
                    otherlv_2=(Token)match(input,TOP,FOLLOW_16); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_2, grammarAccess.getSelectAccess().getTOPKeyword_2_0());
                          
                    }
                    // InternalSqlParser.g:747:1: (this_INT_3= RULE_INT | this_SIGNED_DOUBLE_4= RULE_SIGNED_DOUBLE )
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==RULE_INT) ) {
                        alt13=1;
                    }
                    else if ( (LA13_0==RULE_SIGNED_DOUBLE) ) {
                        alt13=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 13, 0, input);

                        throw nvae;
                    }
                    switch (alt13) {
                        case 1 :
                            // InternalSqlParser.g:747:2: this_INT_3= RULE_INT
                            {
                            this_INT_3=(Token)match(input,RULE_INT,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {
                               
                                  newLeafNode(this_INT_3, grammarAccess.getSelectAccess().getINTTerminalRuleCall_2_1_0()); 
                                  
                            }

                            }
                            break;
                        case 2 :
                            // InternalSqlParser.g:752:6: this_SIGNED_DOUBLE_4= RULE_SIGNED_DOUBLE
                            {
                            this_SIGNED_DOUBLE_4=(Token)match(input,RULE_SIGNED_DOUBLE,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {
                               
                                  newLeafNode(this_SIGNED_DOUBLE_4, grammarAccess.getSelectAccess().getSIGNED_DOUBLETerminalRuleCall_2_1_1()); 
                                  
                            }

                            }
                            break;

                    }

                    // InternalSqlParser.g:756:2: (otherlv_5= PERCENT )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==PERCENT) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // InternalSqlParser.g:757:2: otherlv_5= PERCENT
                            {
                            otherlv_5=(Token)match(input,PERCENT,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                  	newLeafNode(otherlv_5, grammarAccess.getSelectAccess().getPERCENTKeyword_2_2());
                                  
                            }

                            }
                            break;

                    }

                    // InternalSqlParser.g:761:3: (otherlv_6= WITH otherlv_7= TIES )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==WITH) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // InternalSqlParser.g:762:2: otherlv_6= WITH otherlv_7= TIES
                            {
                            otherlv_6=(Token)match(input,WITH,FOLLOW_19); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                  	newLeafNode(otherlv_6, grammarAccess.getSelectAccess().getWITHKeyword_2_3_0());
                                  
                            }
                            otherlv_7=(Token)match(input,TIES,FOLLOW_15); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                  	newLeafNode(otherlv_7, grammarAccess.getSelectAccess().getTIESKeyword_2_3_1());
                                  
                            }

                            }
                            break;

                    }


                    }
                    break;

            }

            // InternalSqlParser.g:771:5: ( (lv_cols_8_0= ruleColumns ) )
            // InternalSqlParser.g:772:1: (lv_cols_8_0= ruleColumns )
            {
            // InternalSqlParser.g:772:1: (lv_cols_8_0= ruleColumns )
            // InternalSqlParser.g:773:3: lv_cols_8_0= ruleColumns
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getSelectAccess().getColsColumnsParserRuleCall_3_0()); 
              	    
            }
            pushFollow(FOLLOW_20);
            lv_cols_8_0=ruleColumns();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getSelectRule());
              	        }
                     		set(
                     			current, 
                     			"cols",
                      		lv_cols_8_0, 
                      		"com.jaspersoft.studio.data.Sql.Columns");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_9=(Token)match(input,FROM,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_9, grammarAccess.getSelectAccess().getFROMKeyword_4());
                  
            }
            // InternalSqlParser.g:794:1: ( (lv_tbl_10_0= ruleTables ) )
            // InternalSqlParser.g:795:1: (lv_tbl_10_0= ruleTables )
            {
            // InternalSqlParser.g:795:1: (lv_tbl_10_0= ruleTables )
            // InternalSqlParser.g:796:3: lv_tbl_10_0= ruleTables
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getSelectAccess().getTblTablesParserRuleCall_5_0()); 
              	    
            }
            pushFollow(FOLLOW_22);
            lv_tbl_10_0=ruleTables();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getSelectRule());
              	        }
                     		set(
                     			current, 
                     			"tbl",
                      		lv_tbl_10_0, 
                      		"com.jaspersoft.studio.data.Sql.Tables");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:812:2: (otherlv_11= WHERE ( (lv_whereExpression_12_0= ruleFullExpression ) ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==WHERE) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalSqlParser.g:813:2: otherlv_11= WHERE ( (lv_whereExpression_12_0= ruleFullExpression ) )
                    {
                    otherlv_11=(Token)match(input,WHERE,FOLLOW_23); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_11, grammarAccess.getSelectAccess().getWHEREKeyword_6_0());
                          
                    }
                    // InternalSqlParser.g:817:1: ( (lv_whereExpression_12_0= ruleFullExpression ) )
                    // InternalSqlParser.g:818:1: (lv_whereExpression_12_0= ruleFullExpression )
                    {
                    // InternalSqlParser.g:818:1: (lv_whereExpression_12_0= ruleFullExpression )
                    // InternalSqlParser.g:819:3: lv_whereExpression_12_0= ruleFullExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getSelectAccess().getWhereExpressionFullExpressionParserRuleCall_6_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_24);
                    lv_whereExpression_12_0=ruleFullExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getSelectRule());
                      	        }
                             		set(
                             			current, 
                             			"whereExpression",
                              		lv_whereExpression_12_0, 
                              		"com.jaspersoft.studio.data.Sql.FullExpression");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalSqlParser.g:835:4: (otherlv_13= GROUP otherlv_14= BY ( (lv_groupByEntry_15_0= ruleGroupByColumns ) ) )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==GROUP) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalSqlParser.g:836:2: otherlv_13= GROUP otherlv_14= BY ( (lv_groupByEntry_15_0= ruleGroupByColumns ) )
                    {
                    otherlv_13=(Token)match(input,GROUP,FOLLOW_25); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_13, grammarAccess.getSelectAccess().getGROUPKeyword_7_0());
                          
                    }
                    otherlv_14=(Token)match(input,BY,FOLLOW_26); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_14, grammarAccess.getSelectAccess().getBYKeyword_7_1());
                          
                    }
                    // InternalSqlParser.g:845:1: ( (lv_groupByEntry_15_0= ruleGroupByColumns ) )
                    // InternalSqlParser.g:846:1: (lv_groupByEntry_15_0= ruleGroupByColumns )
                    {
                    // InternalSqlParser.g:846:1: (lv_groupByEntry_15_0= ruleGroupByColumns )
                    // InternalSqlParser.g:847:3: lv_groupByEntry_15_0= ruleGroupByColumns
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getSelectAccess().getGroupByEntryGroupByColumnsParserRuleCall_7_2_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_27);
                    lv_groupByEntry_15_0=ruleGroupByColumns();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getSelectRule());
                      	        }
                             		set(
                             			current, 
                             			"groupByEntry",
                              		lv_groupByEntry_15_0, 
                              		"com.jaspersoft.studio.data.Sql.GroupByColumns");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalSqlParser.g:863:4: (otherlv_16= HAVING ( (lv_havingEntry_17_0= ruleFullExpression ) ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==HAVING) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalSqlParser.g:864:2: otherlv_16= HAVING ( (lv_havingEntry_17_0= ruleFullExpression ) )
                    {
                    otherlv_16=(Token)match(input,HAVING,FOLLOW_23); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_16, grammarAccess.getSelectAccess().getHAVINGKeyword_8_0());
                          
                    }
                    // InternalSqlParser.g:868:1: ( (lv_havingEntry_17_0= ruleFullExpression ) )
                    // InternalSqlParser.g:869:1: (lv_havingEntry_17_0= ruleFullExpression )
                    {
                    // InternalSqlParser.g:869:1: (lv_havingEntry_17_0= ruleFullExpression )
                    // InternalSqlParser.g:870:3: lv_havingEntry_17_0= ruleFullExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getSelectAccess().getHavingEntryFullExpressionParserRuleCall_8_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_28);
                    lv_havingEntry_17_0=ruleFullExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getSelectRule());
                      	        }
                             		set(
                             			current, 
                             			"havingEntry",
                              		lv_havingEntry_17_0, 
                              		"com.jaspersoft.studio.data.Sql.FullExpression");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalSqlParser.g:886:4: (otherlv_18= ORDER otherlv_19= BY ( (lv_orderByEntry_20_0= ruleOrderByColumns ) ) )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==ORDER) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalSqlParser.g:887:2: otherlv_18= ORDER otherlv_19= BY ( (lv_orderByEntry_20_0= ruleOrderByColumns ) )
                    {
                    otherlv_18=(Token)match(input,ORDER,FOLLOW_25); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_18, grammarAccess.getSelectAccess().getORDERKeyword_9_0());
                          
                    }
                    otherlv_19=(Token)match(input,BY,FOLLOW_26); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_19, grammarAccess.getSelectAccess().getBYKeyword_9_1());
                          
                    }
                    // InternalSqlParser.g:896:1: ( (lv_orderByEntry_20_0= ruleOrderByColumns ) )
                    // InternalSqlParser.g:897:1: (lv_orderByEntry_20_0= ruleOrderByColumns )
                    {
                    // InternalSqlParser.g:897:1: (lv_orderByEntry_20_0= ruleOrderByColumns )
                    // InternalSqlParser.g:898:3: lv_orderByEntry_20_0= ruleOrderByColumns
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getSelectAccess().getOrderByEntryOrderByColumnsParserRuleCall_9_2_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_29);
                    lv_orderByEntry_20_0=ruleOrderByColumns();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getSelectRule());
                      	        }
                             		set(
                             			current, 
                             			"orderByEntry",
                              		lv_orderByEntry_20_0, 
                              		"com.jaspersoft.studio.data.Sql.OrderByColumns");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalSqlParser.g:914:4: (otherlv_21= LIMIT ( (lv_lim_22_0= ruleLimit ) ) )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==LIMIT) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalSqlParser.g:915:2: otherlv_21= LIMIT ( (lv_lim_22_0= ruleLimit ) )
                    {
                    otherlv_21=(Token)match(input,LIMIT,FOLLOW_30); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_21, grammarAccess.getSelectAccess().getLIMITKeyword_10_0());
                          
                    }
                    // InternalSqlParser.g:919:1: ( (lv_lim_22_0= ruleLimit ) )
                    // InternalSqlParser.g:920:1: (lv_lim_22_0= ruleLimit )
                    {
                    // InternalSqlParser.g:920:1: (lv_lim_22_0= ruleLimit )
                    // InternalSqlParser.g:921:3: lv_lim_22_0= ruleLimit
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getSelectAccess().getLimLimitParserRuleCall_10_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_31);
                    lv_lim_22_0=ruleLimit();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getSelectRule());
                      	        }
                             		set(
                             			current, 
                             			"lim",
                              		lv_lim_22_0, 
                              		"com.jaspersoft.studio.data.Sql.Limit");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalSqlParser.g:937:4: (otherlv_23= OFFSET ( (lv_offset_24_0= ruleOffset ) ) )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==OFFSET) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalSqlParser.g:938:2: otherlv_23= OFFSET ( (lv_offset_24_0= ruleOffset ) )
                    {
                    otherlv_23=(Token)match(input,OFFSET,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_23, grammarAccess.getSelectAccess().getOFFSETKeyword_11_0());
                          
                    }
                    // InternalSqlParser.g:942:1: ( (lv_offset_24_0= ruleOffset ) )
                    // InternalSqlParser.g:943:1: (lv_offset_24_0= ruleOffset )
                    {
                    // InternalSqlParser.g:943:1: (lv_offset_24_0= ruleOffset )
                    // InternalSqlParser.g:944:3: lv_offset_24_0= ruleOffset
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getSelectAccess().getOffsetOffsetParserRuleCall_11_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_33);
                    lv_offset_24_0=ruleOffset();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getSelectRule());
                      	        }
                             		set(
                             			current, 
                             			"offset",
                              		lv_offset_24_0, 
                              		"com.jaspersoft.studio.data.Sql.Offset");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalSqlParser.g:960:4: (otherlv_25= FETCH otherlv_26= FIRST ( (lv_fetchFirst_27_0= ruleFetchFirst ) ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==FETCH) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalSqlParser.g:961:2: otherlv_25= FETCH otherlv_26= FIRST ( (lv_fetchFirst_27_0= ruleFetchFirst ) )
                    {
                    otherlv_25=(Token)match(input,FETCH,FOLLOW_34); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_25, grammarAccess.getSelectAccess().getFETCHKeyword_12_0());
                          
                    }
                    otherlv_26=(Token)match(input,FIRST,FOLLOW_12); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_26, grammarAccess.getSelectAccess().getFIRSTKeyword_12_1());
                          
                    }
                    // InternalSqlParser.g:970:1: ( (lv_fetchFirst_27_0= ruleFetchFirst ) )
                    // InternalSqlParser.g:971:1: (lv_fetchFirst_27_0= ruleFetchFirst )
                    {
                    // InternalSqlParser.g:971:1: (lv_fetchFirst_27_0= ruleFetchFirst )
                    // InternalSqlParser.g:972:3: lv_fetchFirst_27_0= ruleFetchFirst
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getSelectAccess().getFetchFirstFetchFirstParserRuleCall_12_2_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_fetchFirst_27_0=ruleFetchFirst();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getSelectRule());
                      	        }
                             		set(
                             			current, 
                             			"fetchFirst",
                              		lv_fetchFirst_27_0, 
                              		"com.jaspersoft.studio.data.Sql.FetchFirst");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSelect"


    // $ANTLR start "entryRuleColumns"
    // InternalSqlParser.g:996:1: entryRuleColumns returns [EObject current=null] : iv_ruleColumns= ruleColumns EOF ;
    public final EObject entryRuleColumns() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleColumns = null;


        try {
            // InternalSqlParser.g:997:2: (iv_ruleColumns= ruleColumns EOF )
            // InternalSqlParser.g:998:2: iv_ruleColumns= ruleColumns EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getColumnsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleColumns=ruleColumns();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleColumns; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleColumns"


    // $ANTLR start "ruleColumns"
    // InternalSqlParser.g:1005:1: ruleColumns returns [EObject current=null] : (this_ColumnOrAlias_0= ruleColumnOrAlias ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleColumnOrAlias ) ) )+ )? ) ;
    public final EObject ruleColumns() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_ColumnOrAlias_0 = null;

        EObject lv_entries_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:1008:28: ( (this_ColumnOrAlias_0= ruleColumnOrAlias ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleColumnOrAlias ) ) )+ )? ) )
            // InternalSqlParser.g:1009:1: (this_ColumnOrAlias_0= ruleColumnOrAlias ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleColumnOrAlias ) ) )+ )? )
            {
            // InternalSqlParser.g:1009:1: (this_ColumnOrAlias_0= ruleColumnOrAlias ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleColumnOrAlias ) ) )+ )? )
            // InternalSqlParser.g:1010:2: this_ColumnOrAlias_0= ruleColumnOrAlias ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleColumnOrAlias ) ) )+ )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getColumnsAccess().getColumnOrAliasParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_9);
            this_ColumnOrAlias_0=ruleColumnOrAlias();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_ColumnOrAlias_0;
                      afterParserOrEnumRuleCall();
                  
            }
            // InternalSqlParser.g:1021:1: ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleColumnOrAlias ) ) )+ )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==Comma) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalSqlParser.g:1021:2: () (otherlv_2= Comma ( (lv_entries_3_0= ruleColumnOrAlias ) ) )+
                    {
                    // InternalSqlParser.g:1021:2: ()
                    // InternalSqlParser.g:1022:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getColumnsAccess().getOrColumnEntriesAction_1_0(),
                                  current);
                          
                    }

                    }

                    // InternalSqlParser.g:1030:2: (otherlv_2= Comma ( (lv_entries_3_0= ruleColumnOrAlias ) ) )+
                    int cnt24=0;
                    loop24:
                    do {
                        int alt24=2;
                        int LA24_0 = input.LA(1);

                        if ( (LA24_0==Comma) ) {
                            alt24=1;
                        }


                        switch (alt24) {
                    	case 1 :
                    	    // InternalSqlParser.g:1031:2: otherlv_2= Comma ( (lv_entries_3_0= ruleColumnOrAlias ) )
                    	    {
                    	    otherlv_2=(Token)match(input,Comma,FOLLOW_15); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_2, grammarAccess.getColumnsAccess().getCommaKeyword_1_1_0());
                    	          
                    	    }
                    	    // InternalSqlParser.g:1035:1: ( (lv_entries_3_0= ruleColumnOrAlias ) )
                    	    // InternalSqlParser.g:1036:1: (lv_entries_3_0= ruleColumnOrAlias )
                    	    {
                    	    // InternalSqlParser.g:1036:1: (lv_entries_3_0= ruleColumnOrAlias )
                    	    // InternalSqlParser.g:1037:3: lv_entries_3_0= ruleColumnOrAlias
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getColumnsAccess().getEntriesColumnOrAliasParserRuleCall_1_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_9);
                    	    lv_entries_3_0=ruleColumnOrAlias();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getColumnsRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"entries",
                    	              		lv_entries_3_0, 
                    	              		"com.jaspersoft.studio.data.Sql.ColumnOrAlias");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt24 >= 1 ) break loop24;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(24, input);
                                throw eee;
                        }
                        cnt24++;
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleColumns"


    // $ANTLR start "entryRuleColumnOrAlias"
    // InternalSqlParser.g:1061:1: entryRuleColumnOrAlias returns [EObject current=null] : iv_ruleColumnOrAlias= ruleColumnOrAlias EOF ;
    public final EObject entryRuleColumnOrAlias() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleColumnOrAlias = null;


        try {
            // InternalSqlParser.g:1062:2: (iv_ruleColumnOrAlias= ruleColumnOrAlias EOF )
            // InternalSqlParser.g:1063:2: iv_ruleColumnOrAlias= ruleColumnOrAlias EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getColumnOrAliasRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleColumnOrAlias=ruleColumnOrAlias();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleColumnOrAlias; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleColumnOrAlias"


    // $ANTLR start "ruleColumnOrAlias"
    // InternalSqlParser.g:1070:1: ruleColumnOrAlias returns [EObject current=null] : ( ( ( (lv_ce_0_0= ruleOperandGroup ) ) ( (lv_alias_1_0= AS ) )? ( (lv_colAlias_2_0= ruleDbObjectName ) )? ) | ( (lv_allCols_3_0= RULE_STAR ) ) | ( (lv_dbAllCols_4_0= ruleDbObjectNameAll ) ) ) ;
    public final EObject ruleColumnOrAlias() throws RecognitionException {
        EObject current = null;

        Token lv_alias_1_0=null;
        Token lv_allCols_3_0=null;
        EObject lv_ce_0_0 = null;

        EObject lv_colAlias_2_0 = null;

        EObject lv_dbAllCols_4_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:1073:28: ( ( ( ( (lv_ce_0_0= ruleOperandGroup ) ) ( (lv_alias_1_0= AS ) )? ( (lv_colAlias_2_0= ruleDbObjectName ) )? ) | ( (lv_allCols_3_0= RULE_STAR ) ) | ( (lv_dbAllCols_4_0= ruleDbObjectNameAll ) ) ) )
            // InternalSqlParser.g:1074:1: ( ( ( (lv_ce_0_0= ruleOperandGroup ) ) ( (lv_alias_1_0= AS ) )? ( (lv_colAlias_2_0= ruleDbObjectName ) )? ) | ( (lv_allCols_3_0= RULE_STAR ) ) | ( (lv_dbAllCols_4_0= ruleDbObjectNameAll ) ) )
            {
            // InternalSqlParser.g:1074:1: ( ( ( (lv_ce_0_0= ruleOperandGroup ) ) ( (lv_alias_1_0= AS ) )? ( (lv_colAlias_2_0= ruleDbObjectName ) )? ) | ( (lv_allCols_3_0= RULE_STAR ) ) | ( (lv_dbAllCols_4_0= ruleDbObjectNameAll ) ) )
            int alt28=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA28_1 = input.LA(2);

                if ( (LA28_1==EOF||LA28_1==FROM||LA28_1==LeftParenthesisPlusSignRightParenthesis||LA28_1==AS||(LA28_1>=VerticalLineVerticalLine && LA28_1<=HyphenMinus)||LA28_1==Solidus||LA28_1==RULE_STAR||(LA28_1>=RULE_STRING && LA28_1<=RULE_ID)) ) {
                    alt28=1;
                }
                else if ( (LA28_1==FullStop) ) {
                    int LA28_6 = input.LA(3);

                    if ( ((LA28_6>=RULE_STRING && LA28_6<=RULE_ID)) ) {
                        alt28=1;
                    }
                    else if ( (LA28_6==RULE_STAR) ) {
                        alt28=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 28, 6, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 28, 1, input);

                    throw nvae;
                }
                }
                break;
            case RULE_DBNAME:
                {
                int LA28_2 = input.LA(2);

                if ( (LA28_2==FullStop) ) {
                    int LA28_6 = input.LA(3);

                    if ( ((LA28_6>=RULE_STRING && LA28_6<=RULE_ID)) ) {
                        alt28=1;
                    }
                    else if ( (LA28_6==RULE_STAR) ) {
                        alt28=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 28, 6, input);

                        throw nvae;
                    }
                }
                else if ( (LA28_2==EOF||LA28_2==FROM||LA28_2==LeftParenthesisPlusSignRightParenthesis||LA28_2==AS||LA28_2==VerticalLineVerticalLine||(LA28_2>=RightParenthesis && LA28_2<=HyphenMinus)||LA28_2==Solidus||LA28_2==RULE_STAR||(LA28_2>=RULE_STRING && LA28_2<=RULE_ID)) ) {
                    alt28=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 28, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_STRING:
                {
                int LA28_3 = input.LA(2);

                if ( (LA28_3==FullStop) ) {
                    int LA28_6 = input.LA(3);

                    if ( ((LA28_6>=RULE_STRING && LA28_6<=RULE_ID)) ) {
                        alt28=1;
                    }
                    else if ( (LA28_6==RULE_STAR) ) {
                        alt28=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 28, 6, input);

                        throw nvae;
                    }
                }
                else if ( (LA28_3==EOF||LA28_3==FROM||LA28_3==LeftParenthesisPlusSignRightParenthesis||LA28_3==AS||LA28_3==VerticalLineVerticalLine||(LA28_3>=RightParenthesis && LA28_3<=HyphenMinus)||LA28_3==Solidus||LA28_3==RULE_STAR||(LA28_3>=RULE_STRING && LA28_3<=RULE_ID)) ) {
                    alt28=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 28, 3, input);

                    throw nvae;
                }
                }
                break;
            case EXTRACT:
            case CAST:
            case CASE:
            case LeftParenthesis:
            case RULE_JRPARAM:
            case RULE_JRNPARAM:
            case RULE_UNSIGNED:
            case RULE_INT:
            case RULE_SIGNED_DOUBLE:
            case RULE_STRING_:
                {
                alt28=1;
                }
                break;
            case RULE_STAR:
                {
                alt28=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }

            switch (alt28) {
                case 1 :
                    // InternalSqlParser.g:1074:2: ( ( (lv_ce_0_0= ruleOperandGroup ) ) ( (lv_alias_1_0= AS ) )? ( (lv_colAlias_2_0= ruleDbObjectName ) )? )
                    {
                    // InternalSqlParser.g:1074:2: ( ( (lv_ce_0_0= ruleOperandGroup ) ) ( (lv_alias_1_0= AS ) )? ( (lv_colAlias_2_0= ruleDbObjectName ) )? )
                    // InternalSqlParser.g:1074:3: ( (lv_ce_0_0= ruleOperandGroup ) ) ( (lv_alias_1_0= AS ) )? ( (lv_colAlias_2_0= ruleDbObjectName ) )?
                    {
                    // InternalSqlParser.g:1074:3: ( (lv_ce_0_0= ruleOperandGroup ) )
                    // InternalSqlParser.g:1075:1: (lv_ce_0_0= ruleOperandGroup )
                    {
                    // InternalSqlParser.g:1075:1: (lv_ce_0_0= ruleOperandGroup )
                    // InternalSqlParser.g:1076:3: lv_ce_0_0= ruleOperandGroup
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getColumnOrAliasAccess().getCeOperandGroupParserRuleCall_0_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_35);
                    lv_ce_0_0=ruleOperandGroup();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getColumnOrAliasRule());
                      	        }
                             		set(
                             			current, 
                             			"ce",
                              		lv_ce_0_0, 
                              		"com.jaspersoft.studio.data.Sql.OperandGroup");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }

                    // InternalSqlParser.g:1092:2: ( (lv_alias_1_0= AS ) )?
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==AS) ) {
                        alt26=1;
                    }
                    switch (alt26) {
                        case 1 :
                            // InternalSqlParser.g:1093:1: (lv_alias_1_0= AS )
                            {
                            // InternalSqlParser.g:1093:1: (lv_alias_1_0= AS )
                            // InternalSqlParser.g:1094:3: lv_alias_1_0= AS
                            {
                            lv_alias_1_0=(Token)match(input,AS,FOLLOW_36); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                      newLeafNode(lv_alias_1_0, grammarAccess.getColumnOrAliasAccess().getAliasASKeyword_0_1_0());
                                  
                            }
                            if ( state.backtracking==0 ) {

                              	        if (current==null) {
                              	            current = createModelElement(grammarAccess.getColumnOrAliasRule());
                              	        }
                                     		setWithLastConsumed(current, "alias", lv_alias_1_0, "AS");
                              	    
                            }

                            }


                            }
                            break;

                    }

                    // InternalSqlParser.g:1108:3: ( (lv_colAlias_2_0= ruleDbObjectName ) )?
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( ((LA27_0>=RULE_STRING && LA27_0<=RULE_ID)) ) {
                        alt27=1;
                    }
                    switch (alt27) {
                        case 1 :
                            // InternalSqlParser.g:1109:1: (lv_colAlias_2_0= ruleDbObjectName )
                            {
                            // InternalSqlParser.g:1109:1: (lv_colAlias_2_0= ruleDbObjectName )
                            // InternalSqlParser.g:1110:3: lv_colAlias_2_0= ruleDbObjectName
                            {
                            if ( state.backtracking==0 ) {
                               
                              	        newCompositeNode(grammarAccess.getColumnOrAliasAccess().getColAliasDbObjectNameParserRuleCall_0_2_0()); 
                              	    
                            }
                            pushFollow(FOLLOW_2);
                            lv_colAlias_2_0=ruleDbObjectName();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              	        if (current==null) {
                              	            current = createModelElementForParent(grammarAccess.getColumnOrAliasRule());
                              	        }
                                     		set(
                                     			current, 
                                     			"colAlias",
                                      		lv_colAlias_2_0, 
                                      		"com.jaspersoft.studio.data.Sql.DbObjectName");
                              	        afterParserOrEnumRuleCall();
                              	    
                            }

                            }


                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:1127:6: ( (lv_allCols_3_0= RULE_STAR ) )
                    {
                    // InternalSqlParser.g:1127:6: ( (lv_allCols_3_0= RULE_STAR ) )
                    // InternalSqlParser.g:1128:1: (lv_allCols_3_0= RULE_STAR )
                    {
                    // InternalSqlParser.g:1128:1: (lv_allCols_3_0= RULE_STAR )
                    // InternalSqlParser.g:1129:3: lv_allCols_3_0= RULE_STAR
                    {
                    lv_allCols_3_0=(Token)match(input,RULE_STAR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(lv_allCols_3_0, grammarAccess.getColumnOrAliasAccess().getAllColsSTARTerminalRuleCall_1_0()); 
                      		
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getColumnOrAliasRule());
                      	        }
                             		setWithLastConsumed(
                             			current, 
                             			"allCols",
                              		lv_allCols_3_0, 
                              		"com.jaspersoft.studio.data.Sql.STAR");
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSqlParser.g:1146:6: ( (lv_dbAllCols_4_0= ruleDbObjectNameAll ) )
                    {
                    // InternalSqlParser.g:1146:6: ( (lv_dbAllCols_4_0= ruleDbObjectNameAll ) )
                    // InternalSqlParser.g:1147:1: (lv_dbAllCols_4_0= ruleDbObjectNameAll )
                    {
                    // InternalSqlParser.g:1147:1: (lv_dbAllCols_4_0= ruleDbObjectNameAll )
                    // InternalSqlParser.g:1148:3: lv_dbAllCols_4_0= ruleDbObjectNameAll
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getColumnOrAliasAccess().getDbAllColsDbObjectNameAllParserRuleCall_2_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_dbAllCols_4_0=ruleDbObjectNameAll();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getColumnOrAliasRule());
                      	        }
                             		set(
                             			current, 
                             			"dbAllCols",
                              		lv_dbAllCols_4_0, 
                              		"com.jaspersoft.studio.data.Sql.DbObjectNameAll");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleColumnOrAlias"


    // $ANTLR start "entryRuleColumnFull"
    // InternalSqlParser.g:1172:1: entryRuleColumnFull returns [EObject current=null] : iv_ruleColumnFull= ruleColumnFull EOF ;
    public final EObject entryRuleColumnFull() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleColumnFull = null;


        try {
            // InternalSqlParser.g:1173:2: (iv_ruleColumnFull= ruleColumnFull EOF )
            // InternalSqlParser.g:1174:2: iv_ruleColumnFull= ruleColumnFull EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getColumnFullRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleColumnFull=ruleColumnFull();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleColumnFull; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleColumnFull"


    // $ANTLR start "ruleColumnFull"
    // InternalSqlParser.g:1181:1: ruleColumnFull returns [EObject current=null] : (this_DbObjectName_0= ruleDbObjectName ( () (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )? ) ;
    public final EObject ruleColumnFull() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_DbObjectName_0 = null;

        EObject lv_entries_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:1184:28: ( (this_DbObjectName_0= ruleDbObjectName ( () (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )? ) )
            // InternalSqlParser.g:1185:1: (this_DbObjectName_0= ruleDbObjectName ( () (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )? )
            {
            // InternalSqlParser.g:1185:1: (this_DbObjectName_0= ruleDbObjectName ( () (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )? )
            // InternalSqlParser.g:1186:2: this_DbObjectName_0= ruleDbObjectName ( () (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getColumnFullAccess().getDbObjectNameParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_37);
            this_DbObjectName_0=ruleDbObjectName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_DbObjectName_0;
                      afterParserOrEnumRuleCall();
                  
            }
            // InternalSqlParser.g:1197:1: ( () (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==FullStop) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalSqlParser.g:1197:2: () (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+
                    {
                    // InternalSqlParser.g:1197:2: ()
                    // InternalSqlParser.g:1198:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getColumnFullAccess().getColEntriesAction_1_0(),
                                  current);
                          
                    }

                    }

                    // InternalSqlParser.g:1206:2: (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+
                    int cnt29=0;
                    loop29:
                    do {
                        int alt29=2;
                        int LA29_0 = input.LA(1);

                        if ( (LA29_0==FullStop) ) {
                            alt29=1;
                        }


                        switch (alt29) {
                    	case 1 :
                    	    // InternalSqlParser.g:1207:2: otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) )
                    	    {
                    	    otherlv_2=(Token)match(input,FullStop,FOLLOW_4); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_2, grammarAccess.getColumnFullAccess().getFullStopKeyword_1_1_0());
                    	          
                    	    }
                    	    // InternalSqlParser.g:1211:1: ( (lv_entries_3_0= ruleDbObjectName ) )
                    	    // InternalSqlParser.g:1212:1: (lv_entries_3_0= ruleDbObjectName )
                    	    {
                    	    // InternalSqlParser.g:1212:1: (lv_entries_3_0= ruleDbObjectName )
                    	    // InternalSqlParser.g:1213:3: lv_entries_3_0= ruleDbObjectName
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getColumnFullAccess().getEntriesDbObjectNameParserRuleCall_1_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_37);
                    	    lv_entries_3_0=ruleDbObjectName();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getColumnFullRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"entries",
                    	              		lv_entries_3_0, 
                    	              		"com.jaspersoft.studio.data.Sql.DbObjectName");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt29 >= 1 ) break loop29;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(29, input);
                                throw eee;
                        }
                        cnt29++;
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleColumnFull"


    // $ANTLR start "entryRuleTables"
    // InternalSqlParser.g:1237:1: entryRuleTables returns [EObject current=null] : iv_ruleTables= ruleTables EOF ;
    public final EObject entryRuleTables() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTables = null;


        try {
            // InternalSqlParser.g:1238:2: (iv_ruleTables= ruleTables EOF )
            // InternalSqlParser.g:1239:2: iv_ruleTables= ruleTables EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTablesRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTables=ruleTables();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTables; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTables"


    // $ANTLR start "ruleTables"
    // InternalSqlParser.g:1246:1: ruleTables returns [EObject current=null] : (this_FromTable_0= ruleFromTable ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleFromTable ) ) )+ )? ) ;
    public final EObject ruleTables() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_FromTable_0 = null;

        EObject lv_entries_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:1249:28: ( (this_FromTable_0= ruleFromTable ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleFromTable ) ) )+ )? ) )
            // InternalSqlParser.g:1250:1: (this_FromTable_0= ruleFromTable ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleFromTable ) ) )+ )? )
            {
            // InternalSqlParser.g:1250:1: (this_FromTable_0= ruleFromTable ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleFromTable ) ) )+ )? )
            // InternalSqlParser.g:1251:2: this_FromTable_0= ruleFromTable ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleFromTable ) ) )+ )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getTablesAccess().getFromTableParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_9);
            this_FromTable_0=ruleFromTable();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_FromTable_0;
                      afterParserOrEnumRuleCall();
                  
            }
            // InternalSqlParser.g:1262:1: ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleFromTable ) ) )+ )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==Comma) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalSqlParser.g:1262:2: () (otherlv_2= Comma ( (lv_entries_3_0= ruleFromTable ) ) )+
                    {
                    // InternalSqlParser.g:1262:2: ()
                    // InternalSqlParser.g:1263:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getTablesAccess().getOrTableEntriesAction_1_0(),
                                  current);
                          
                    }

                    }

                    // InternalSqlParser.g:1271:2: (otherlv_2= Comma ( (lv_entries_3_0= ruleFromTable ) ) )+
                    int cnt31=0;
                    loop31:
                    do {
                        int alt31=2;
                        int LA31_0 = input.LA(1);

                        if ( (LA31_0==Comma) ) {
                            alt31=1;
                        }


                        switch (alt31) {
                    	case 1 :
                    	    // InternalSqlParser.g:1272:2: otherlv_2= Comma ( (lv_entries_3_0= ruleFromTable ) )
                    	    {
                    	    otherlv_2=(Token)match(input,Comma,FOLLOW_21); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_2, grammarAccess.getTablesAccess().getCommaKeyword_1_1_0());
                    	          
                    	    }
                    	    // InternalSqlParser.g:1276:1: ( (lv_entries_3_0= ruleFromTable ) )
                    	    // InternalSqlParser.g:1277:1: (lv_entries_3_0= ruleFromTable )
                    	    {
                    	    // InternalSqlParser.g:1277:1: (lv_entries_3_0= ruleFromTable )
                    	    // InternalSqlParser.g:1278:3: lv_entries_3_0= ruleFromTable
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getTablesAccess().getEntriesFromTableParserRuleCall_1_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_9);
                    	    lv_entries_3_0=ruleFromTable();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getTablesRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"entries",
                    	              		lv_entries_3_0, 
                    	              		"com.jaspersoft.studio.data.Sql.FromTable");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt31 >= 1 ) break loop31;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(31, input);
                                throw eee;
                        }
                        cnt31++;
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTables"


    // $ANTLR start "entryRuleFromTable"
    // InternalSqlParser.g:1302:1: entryRuleFromTable returns [EObject current=null] : iv_ruleFromTable= ruleFromTable EOF ;
    public final EObject entryRuleFromTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFromTable = null;


        try {
            // InternalSqlParser.g:1303:2: (iv_ruleFromTable= ruleFromTable EOF )
            // InternalSqlParser.g:1304:2: iv_ruleFromTable= ruleFromTable EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFromTableRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleFromTable=ruleFromTable();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFromTable; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFromTable"


    // $ANTLR start "ruleFromTable"
    // InternalSqlParser.g:1311:1: ruleFromTable returns [EObject current=null] : ( ( (lv_table_0_0= ruleTableOrAlias ) ) ( (lv_fjoin_1_0= ruleFromTableJoin ) )* ) ;
    public final EObject ruleFromTable() throws RecognitionException {
        EObject current = null;

        EObject lv_table_0_0 = null;

        EObject lv_fjoin_1_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:1314:28: ( ( ( (lv_table_0_0= ruleTableOrAlias ) ) ( (lv_fjoin_1_0= ruleFromTableJoin ) )* ) )
            // InternalSqlParser.g:1315:1: ( ( (lv_table_0_0= ruleTableOrAlias ) ) ( (lv_fjoin_1_0= ruleFromTableJoin ) )* )
            {
            // InternalSqlParser.g:1315:1: ( ( (lv_table_0_0= ruleTableOrAlias ) ) ( (lv_fjoin_1_0= ruleFromTableJoin ) )* )
            // InternalSqlParser.g:1315:2: ( (lv_table_0_0= ruleTableOrAlias ) ) ( (lv_fjoin_1_0= ruleFromTableJoin ) )*
            {
            // InternalSqlParser.g:1315:2: ( (lv_table_0_0= ruleTableOrAlias ) )
            // InternalSqlParser.g:1316:1: (lv_table_0_0= ruleTableOrAlias )
            {
            // InternalSqlParser.g:1316:1: (lv_table_0_0= ruleTableOrAlias )
            // InternalSqlParser.g:1317:3: lv_table_0_0= ruleTableOrAlias
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getFromTableAccess().getTableTableOrAliasParserRuleCall_0_0()); 
              	    
            }
            pushFollow(FOLLOW_38);
            lv_table_0_0=ruleTableOrAlias();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getFromTableRule());
              	        }
                     		set(
                     			current, 
                     			"table",
                      		lv_table_0_0, 
                      		"com.jaspersoft.studio.data.Sql.TableOrAlias");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:1333:2: ( (lv_fjoin_1_0= ruleFromTableJoin ) )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==STRAIGHT_JOIN||LA33_0==NATURAL||LA33_0==CROSS||LA33_0==INNER||LA33_0==RIGHT||LA33_0==FULL||LA33_0==JOIN||LA33_0==LEFT) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // InternalSqlParser.g:1334:1: (lv_fjoin_1_0= ruleFromTableJoin )
            	    {
            	    // InternalSqlParser.g:1334:1: (lv_fjoin_1_0= ruleFromTableJoin )
            	    // InternalSqlParser.g:1335:3: lv_fjoin_1_0= ruleFromTableJoin
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getFromTableAccess().getFjoinFromTableJoinParserRuleCall_1_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_38);
            	    lv_fjoin_1_0=ruleFromTableJoin();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getFromTableRule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"fjoin",
            	              		lv_fjoin_1_0, 
            	              		"com.jaspersoft.studio.data.Sql.FromTableJoin");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop33;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFromTable"


    // $ANTLR start "entryRuleFromTableJoin"
    // InternalSqlParser.g:1359:1: entryRuleFromTableJoin returns [EObject current=null] : iv_ruleFromTableJoin= ruleFromTableJoin EOF ;
    public final EObject entryRuleFromTableJoin() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFromTableJoin = null;


        try {
            // InternalSqlParser.g:1360:2: (iv_ruleFromTableJoin= ruleFromTableJoin EOF )
            // InternalSqlParser.g:1361:2: iv_ruleFromTableJoin= ruleFromTableJoin EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFromTableJoinRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleFromTableJoin=ruleFromTableJoin();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFromTableJoin; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFromTableJoin"


    // $ANTLR start "ruleFromTableJoin"
    // InternalSqlParser.g:1368:1: ruleFromTableJoin returns [EObject current=null] : ( ( (lv_join_0_0= ruleJoinType ) ) ( (lv_onTable_1_0= ruleTableOrAlias ) ) ( (otherlv_2= ON ( (lv_joinExpr_3_0= ruleFullExpression ) ) ) | ( (lv_joinCond_4_0= ruleJoinCondition ) ) ) ) ;
    public final EObject ruleFromTableJoin() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_join_0_0 = null;

        EObject lv_onTable_1_0 = null;

        EObject lv_joinExpr_3_0 = null;

        EObject lv_joinCond_4_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:1371:28: ( ( ( (lv_join_0_0= ruleJoinType ) ) ( (lv_onTable_1_0= ruleTableOrAlias ) ) ( (otherlv_2= ON ( (lv_joinExpr_3_0= ruleFullExpression ) ) ) | ( (lv_joinCond_4_0= ruleJoinCondition ) ) ) ) )
            // InternalSqlParser.g:1372:1: ( ( (lv_join_0_0= ruleJoinType ) ) ( (lv_onTable_1_0= ruleTableOrAlias ) ) ( (otherlv_2= ON ( (lv_joinExpr_3_0= ruleFullExpression ) ) ) | ( (lv_joinCond_4_0= ruleJoinCondition ) ) ) )
            {
            // InternalSqlParser.g:1372:1: ( ( (lv_join_0_0= ruleJoinType ) ) ( (lv_onTable_1_0= ruleTableOrAlias ) ) ( (otherlv_2= ON ( (lv_joinExpr_3_0= ruleFullExpression ) ) ) | ( (lv_joinCond_4_0= ruleJoinCondition ) ) ) )
            // InternalSqlParser.g:1372:2: ( (lv_join_0_0= ruleJoinType ) ) ( (lv_onTable_1_0= ruleTableOrAlias ) ) ( (otherlv_2= ON ( (lv_joinExpr_3_0= ruleFullExpression ) ) ) | ( (lv_joinCond_4_0= ruleJoinCondition ) ) )
            {
            // InternalSqlParser.g:1372:2: ( (lv_join_0_0= ruleJoinType ) )
            // InternalSqlParser.g:1373:1: (lv_join_0_0= ruleJoinType )
            {
            // InternalSqlParser.g:1373:1: (lv_join_0_0= ruleJoinType )
            // InternalSqlParser.g:1374:3: lv_join_0_0= ruleJoinType
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getFromTableJoinAccess().getJoinJoinTypeParserRuleCall_0_0()); 
              	    
            }
            pushFollow(FOLLOW_21);
            lv_join_0_0=ruleJoinType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getFromTableJoinRule());
              	        }
                     		set(
                     			current, 
                     			"join",
                      		lv_join_0_0, 
                      		"com.jaspersoft.studio.data.Sql.JoinType");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:1390:2: ( (lv_onTable_1_0= ruleTableOrAlias ) )
            // InternalSqlParser.g:1391:1: (lv_onTable_1_0= ruleTableOrAlias )
            {
            // InternalSqlParser.g:1391:1: (lv_onTable_1_0= ruleTableOrAlias )
            // InternalSqlParser.g:1392:3: lv_onTable_1_0= ruleTableOrAlias
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getFromTableJoinAccess().getOnTableTableOrAliasParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_39);
            lv_onTable_1_0=ruleTableOrAlias();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getFromTableJoinRule());
              	        }
                     		set(
                     			current, 
                     			"onTable",
                      		lv_onTable_1_0, 
                      		"com.jaspersoft.studio.data.Sql.TableOrAlias");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:1408:2: ( (otherlv_2= ON ( (lv_joinExpr_3_0= ruleFullExpression ) ) ) | ( (lv_joinCond_4_0= ruleJoinCondition ) ) )
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==ON) ) {
                alt34=1;
            }
            else if ( (LA34_0==USING) ) {
                alt34=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 34, 0, input);

                throw nvae;
            }
            switch (alt34) {
                case 1 :
                    // InternalSqlParser.g:1408:3: (otherlv_2= ON ( (lv_joinExpr_3_0= ruleFullExpression ) ) )
                    {
                    // InternalSqlParser.g:1408:3: (otherlv_2= ON ( (lv_joinExpr_3_0= ruleFullExpression ) ) )
                    // InternalSqlParser.g:1409:2: otherlv_2= ON ( (lv_joinExpr_3_0= ruleFullExpression ) )
                    {
                    otherlv_2=(Token)match(input,ON,FOLLOW_23); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_2, grammarAccess.getFromTableJoinAccess().getONKeyword_2_0_0());
                          
                    }
                    // InternalSqlParser.g:1413:1: ( (lv_joinExpr_3_0= ruleFullExpression ) )
                    // InternalSqlParser.g:1414:1: (lv_joinExpr_3_0= ruleFullExpression )
                    {
                    // InternalSqlParser.g:1414:1: (lv_joinExpr_3_0= ruleFullExpression )
                    // InternalSqlParser.g:1415:3: lv_joinExpr_3_0= ruleFullExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getFromTableJoinAccess().getJoinExprFullExpressionParserRuleCall_2_0_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_joinExpr_3_0=ruleFullExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getFromTableJoinRule());
                      	        }
                             		set(
                             			current, 
                             			"joinExpr",
                              		lv_joinExpr_3_0, 
                              		"com.jaspersoft.studio.data.Sql.FullExpression");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:1432:6: ( (lv_joinCond_4_0= ruleJoinCondition ) )
                    {
                    // InternalSqlParser.g:1432:6: ( (lv_joinCond_4_0= ruleJoinCondition ) )
                    // InternalSqlParser.g:1433:1: (lv_joinCond_4_0= ruleJoinCondition )
                    {
                    // InternalSqlParser.g:1433:1: (lv_joinCond_4_0= ruleJoinCondition )
                    // InternalSqlParser.g:1434:3: lv_joinCond_4_0= ruleJoinCondition
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getFromTableJoinAccess().getJoinCondJoinConditionParserRuleCall_2_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_joinCond_4_0=ruleJoinCondition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getFromTableJoinRule());
                      	        }
                             		set(
                             			current, 
                             			"joinCond",
                              		lv_joinCond_4_0, 
                              		"com.jaspersoft.studio.data.Sql.JoinCondition");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFromTableJoin"


    // $ANTLR start "entryRuleJoinCondition"
    // InternalSqlParser.g:1458:1: entryRuleJoinCondition returns [EObject current=null] : iv_ruleJoinCondition= ruleJoinCondition EOF ;
    public final EObject entryRuleJoinCondition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJoinCondition = null;


        try {
            // InternalSqlParser.g:1459:2: (iv_ruleJoinCondition= ruleJoinCondition EOF )
            // InternalSqlParser.g:1460:2: iv_ruleJoinCondition= ruleJoinCondition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJoinConditionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleJoinCondition=ruleJoinCondition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJoinCondition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJoinCondition"


    // $ANTLR start "ruleJoinCondition"
    // InternalSqlParser.g:1467:1: ruleJoinCondition returns [EObject current=null] : (otherlv_0= USING otherlv_1= LeftParenthesis ( (lv_useCols_2_0= ruleUsingCols ) ) otherlv_3= RightParenthesis ) ;
    public final EObject ruleJoinCondition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_useCols_2_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:1470:28: ( (otherlv_0= USING otherlv_1= LeftParenthesis ( (lv_useCols_2_0= ruleUsingCols ) ) otherlv_3= RightParenthesis ) )
            // InternalSqlParser.g:1471:1: (otherlv_0= USING otherlv_1= LeftParenthesis ( (lv_useCols_2_0= ruleUsingCols ) ) otherlv_3= RightParenthesis )
            {
            // InternalSqlParser.g:1471:1: (otherlv_0= USING otherlv_1= LeftParenthesis ( (lv_useCols_2_0= ruleUsingCols ) ) otherlv_3= RightParenthesis )
            // InternalSqlParser.g:1472:2: otherlv_0= USING otherlv_1= LeftParenthesis ( (lv_useCols_2_0= ruleUsingCols ) ) otherlv_3= RightParenthesis
            {
            otherlv_0=(Token)match(input,USING,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getJoinConditionAccess().getUSINGKeyword_0());
                  
            }
            otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getJoinConditionAccess().getLeftParenthesisKeyword_1());
                  
            }
            // InternalSqlParser.g:1481:1: ( (lv_useCols_2_0= ruleUsingCols ) )
            // InternalSqlParser.g:1482:1: (lv_useCols_2_0= ruleUsingCols )
            {
            // InternalSqlParser.g:1482:1: (lv_useCols_2_0= ruleUsingCols )
            // InternalSqlParser.g:1483:3: lv_useCols_2_0= ruleUsingCols
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getJoinConditionAccess().getUseColsUsingColsParserRuleCall_2_0()); 
              	    
            }
            pushFollow(FOLLOW_8);
            lv_useCols_2_0=ruleUsingCols();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getJoinConditionRule());
              	        }
                     		set(
                     			current, 
                     			"useCols",
                      		lv_useCols_2_0, 
                      		"com.jaspersoft.studio.data.Sql.UsingCols");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_3=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getJoinConditionAccess().getRightParenthesisKeyword_3());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJoinCondition"


    // $ANTLR start "entryRuleUsingCols"
    // InternalSqlParser.g:1512:1: entryRuleUsingCols returns [EObject current=null] : iv_ruleUsingCols= ruleUsingCols EOF ;
    public final EObject entryRuleUsingCols() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUsingCols = null;


        try {
            // InternalSqlParser.g:1513:2: (iv_ruleUsingCols= ruleUsingCols EOF )
            // InternalSqlParser.g:1514:2: iv_ruleUsingCols= ruleUsingCols EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUsingColsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUsingCols=ruleUsingCols();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUsingCols; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUsingCols"


    // $ANTLR start "ruleUsingCols"
    // InternalSqlParser.g:1521:1: ruleUsingCols returns [EObject current=null] : (this_DbObjectName_0= ruleDbObjectName ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )? ) ;
    public final EObject ruleUsingCols() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_DbObjectName_0 = null;

        EObject lv_entries_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:1524:28: ( (this_DbObjectName_0= ruleDbObjectName ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )? ) )
            // InternalSqlParser.g:1525:1: (this_DbObjectName_0= ruleDbObjectName ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )? )
            {
            // InternalSqlParser.g:1525:1: (this_DbObjectName_0= ruleDbObjectName ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )? )
            // InternalSqlParser.g:1526:2: this_DbObjectName_0= ruleDbObjectName ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getUsingColsAccess().getDbObjectNameParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_9);
            this_DbObjectName_0=ruleDbObjectName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_DbObjectName_0;
                      afterParserOrEnumRuleCall();
                  
            }
            // InternalSqlParser.g:1537:1: ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==Comma) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalSqlParser.g:1537:2: () (otherlv_2= Comma ( (lv_entries_3_0= ruleDbObjectName ) ) )+
                    {
                    // InternalSqlParser.g:1537:2: ()
                    // InternalSqlParser.g:1538:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getUsingColsAccess().getUsingColsEntriesAction_1_0(),
                                  current);
                          
                    }

                    }

                    // InternalSqlParser.g:1546:2: (otherlv_2= Comma ( (lv_entries_3_0= ruleDbObjectName ) ) )+
                    int cnt35=0;
                    loop35:
                    do {
                        int alt35=2;
                        int LA35_0 = input.LA(1);

                        if ( (LA35_0==Comma) ) {
                            alt35=1;
                        }


                        switch (alt35) {
                    	case 1 :
                    	    // InternalSqlParser.g:1547:2: otherlv_2= Comma ( (lv_entries_3_0= ruleDbObjectName ) )
                    	    {
                    	    otherlv_2=(Token)match(input,Comma,FOLLOW_4); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_2, grammarAccess.getUsingColsAccess().getCommaKeyword_1_1_0());
                    	          
                    	    }
                    	    // InternalSqlParser.g:1551:1: ( (lv_entries_3_0= ruleDbObjectName ) )
                    	    // InternalSqlParser.g:1552:1: (lv_entries_3_0= ruleDbObjectName )
                    	    {
                    	    // InternalSqlParser.g:1552:1: (lv_entries_3_0= ruleDbObjectName )
                    	    // InternalSqlParser.g:1553:3: lv_entries_3_0= ruleDbObjectName
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getUsingColsAccess().getEntriesDbObjectNameParserRuleCall_1_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_9);
                    	    lv_entries_3_0=ruleDbObjectName();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getUsingColsRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"entries",
                    	              		lv_entries_3_0, 
                    	              		"com.jaspersoft.studio.data.Sql.DbObjectName");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt35 >= 1 ) break loop35;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(35, input);
                                throw eee;
                        }
                        cnt35++;
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUsingCols"


    // $ANTLR start "entryRuleTableOrAlias"
    // InternalSqlParser.g:1577:1: entryRuleTableOrAlias returns [EObject current=null] : iv_ruleTableOrAlias= ruleTableOrAlias EOF ;
    public final EObject entryRuleTableOrAlias() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableOrAlias = null;


        try {
            // InternalSqlParser.g:1578:2: (iv_ruleTableOrAlias= ruleTableOrAlias EOF )
            // InternalSqlParser.g:1579:2: iv_ruleTableOrAlias= ruleTableOrAlias EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTableOrAliasRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTableOrAlias=ruleTableOrAlias();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTableOrAlias; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTableOrAlias"


    // $ANTLR start "ruleTableOrAlias"
    // InternalSqlParser.g:1586:1: ruleTableOrAlias returns [EObject current=null] : ( ( ( (lv_tfull_0_0= ruleTableFull ) ) | ( (lv_sq_1_0= ruleSubQueryOperand ) ) | ( (lv_values_2_0= ruleFromValues ) ) ) ( ( (lv_pivot_3_0= rulePivotTable ) ) | ( (lv_unpivot_4_0= ruleUnpivotTable ) ) )? ( (lv_alias_5_0= AS ) )? ( (lv_tblAlias_6_0= ruleDbObjectName ) )? ) ;
    public final EObject ruleTableOrAlias() throws RecognitionException {
        EObject current = null;

        Token lv_alias_5_0=null;
        EObject lv_tfull_0_0 = null;

        EObject lv_sq_1_0 = null;

        EObject lv_values_2_0 = null;

        EObject lv_pivot_3_0 = null;

        EObject lv_unpivot_4_0 = null;

        EObject lv_tblAlias_6_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:1589:28: ( ( ( ( (lv_tfull_0_0= ruleTableFull ) ) | ( (lv_sq_1_0= ruleSubQueryOperand ) ) | ( (lv_values_2_0= ruleFromValues ) ) ) ( ( (lv_pivot_3_0= rulePivotTable ) ) | ( (lv_unpivot_4_0= ruleUnpivotTable ) ) )? ( (lv_alias_5_0= AS ) )? ( (lv_tblAlias_6_0= ruleDbObjectName ) )? ) )
            // InternalSqlParser.g:1590:1: ( ( ( (lv_tfull_0_0= ruleTableFull ) ) | ( (lv_sq_1_0= ruleSubQueryOperand ) ) | ( (lv_values_2_0= ruleFromValues ) ) ) ( ( (lv_pivot_3_0= rulePivotTable ) ) | ( (lv_unpivot_4_0= ruleUnpivotTable ) ) )? ( (lv_alias_5_0= AS ) )? ( (lv_tblAlias_6_0= ruleDbObjectName ) )? )
            {
            // InternalSqlParser.g:1590:1: ( ( ( (lv_tfull_0_0= ruleTableFull ) ) | ( (lv_sq_1_0= ruleSubQueryOperand ) ) | ( (lv_values_2_0= ruleFromValues ) ) ) ( ( (lv_pivot_3_0= rulePivotTable ) ) | ( (lv_unpivot_4_0= ruleUnpivotTable ) ) )? ( (lv_alias_5_0= AS ) )? ( (lv_tblAlias_6_0= ruleDbObjectName ) )? )
            // InternalSqlParser.g:1590:2: ( ( (lv_tfull_0_0= ruleTableFull ) ) | ( (lv_sq_1_0= ruleSubQueryOperand ) ) | ( (lv_values_2_0= ruleFromValues ) ) ) ( ( (lv_pivot_3_0= rulePivotTable ) ) | ( (lv_unpivot_4_0= ruleUnpivotTable ) ) )? ( (lv_alias_5_0= AS ) )? ( (lv_tblAlias_6_0= ruleDbObjectName ) )?
            {
            // InternalSqlParser.g:1590:2: ( ( (lv_tfull_0_0= ruleTableFull ) ) | ( (lv_sq_1_0= ruleSubQueryOperand ) ) | ( (lv_values_2_0= ruleFromValues ) ) )
            int alt37=3;
            int LA37_0 = input.LA(1);

            if ( ((LA37_0>=RULE_STRING && LA37_0<=RULE_ID)) ) {
                alt37=1;
            }
            else if ( (LA37_0==LeftParenthesis) ) {
                int LA37_2 = input.LA(2);

                if ( (LA37_2==SELECT) ) {
                    alt37=2;
                }
                else if ( (LA37_2==VALUES) ) {
                    alt37=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 37, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 37, 0, input);

                throw nvae;
            }
            switch (alt37) {
                case 1 :
                    // InternalSqlParser.g:1590:3: ( (lv_tfull_0_0= ruleTableFull ) )
                    {
                    // InternalSqlParser.g:1590:3: ( (lv_tfull_0_0= ruleTableFull ) )
                    // InternalSqlParser.g:1591:1: (lv_tfull_0_0= ruleTableFull )
                    {
                    // InternalSqlParser.g:1591:1: (lv_tfull_0_0= ruleTableFull )
                    // InternalSqlParser.g:1592:3: lv_tfull_0_0= ruleTableFull
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getTableOrAliasAccess().getTfullTableFullParserRuleCall_0_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_40);
                    lv_tfull_0_0=ruleTableFull();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getTableOrAliasRule());
                      	        }
                             		set(
                             			current, 
                             			"tfull",
                              		lv_tfull_0_0, 
                              		"com.jaspersoft.studio.data.Sql.TableFull");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:1609:6: ( (lv_sq_1_0= ruleSubQueryOperand ) )
                    {
                    // InternalSqlParser.g:1609:6: ( (lv_sq_1_0= ruleSubQueryOperand ) )
                    // InternalSqlParser.g:1610:1: (lv_sq_1_0= ruleSubQueryOperand )
                    {
                    // InternalSqlParser.g:1610:1: (lv_sq_1_0= ruleSubQueryOperand )
                    // InternalSqlParser.g:1611:3: lv_sq_1_0= ruleSubQueryOperand
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getTableOrAliasAccess().getSqSubQueryOperandParserRuleCall_0_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_40);
                    lv_sq_1_0=ruleSubQueryOperand();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getTableOrAliasRule());
                      	        }
                             		set(
                             			current, 
                             			"sq",
                              		lv_sq_1_0, 
                              		"com.jaspersoft.studio.data.Sql.SubQueryOperand");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSqlParser.g:1628:6: ( (lv_values_2_0= ruleFromValues ) )
                    {
                    // InternalSqlParser.g:1628:6: ( (lv_values_2_0= ruleFromValues ) )
                    // InternalSqlParser.g:1629:1: (lv_values_2_0= ruleFromValues )
                    {
                    // InternalSqlParser.g:1629:1: (lv_values_2_0= ruleFromValues )
                    // InternalSqlParser.g:1630:3: lv_values_2_0= ruleFromValues
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getTableOrAliasAccess().getValuesFromValuesParserRuleCall_0_2_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_40);
                    lv_values_2_0=ruleFromValues();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getTableOrAliasRule());
                      	        }
                             		set(
                             			current, 
                             			"values",
                              		lv_values_2_0, 
                              		"com.jaspersoft.studio.data.Sql.FromValues");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalSqlParser.g:1646:3: ( ( (lv_pivot_3_0= rulePivotTable ) ) | ( (lv_unpivot_4_0= ruleUnpivotTable ) ) )?
            int alt38=3;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==PIVOT) ) {
                alt38=1;
            }
            else if ( (LA38_0==UNPIVOT) ) {
                alt38=2;
            }
            switch (alt38) {
                case 1 :
                    // InternalSqlParser.g:1646:4: ( (lv_pivot_3_0= rulePivotTable ) )
                    {
                    // InternalSqlParser.g:1646:4: ( (lv_pivot_3_0= rulePivotTable ) )
                    // InternalSqlParser.g:1647:1: (lv_pivot_3_0= rulePivotTable )
                    {
                    // InternalSqlParser.g:1647:1: (lv_pivot_3_0= rulePivotTable )
                    // InternalSqlParser.g:1648:3: lv_pivot_3_0= rulePivotTable
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getTableOrAliasAccess().getPivotPivotTableParserRuleCall_1_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_35);
                    lv_pivot_3_0=rulePivotTable();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getTableOrAliasRule());
                      	        }
                             		set(
                             			current, 
                             			"pivot",
                              		lv_pivot_3_0, 
                              		"com.jaspersoft.studio.data.Sql.PivotTable");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:1665:6: ( (lv_unpivot_4_0= ruleUnpivotTable ) )
                    {
                    // InternalSqlParser.g:1665:6: ( (lv_unpivot_4_0= ruleUnpivotTable ) )
                    // InternalSqlParser.g:1666:1: (lv_unpivot_4_0= ruleUnpivotTable )
                    {
                    // InternalSqlParser.g:1666:1: (lv_unpivot_4_0= ruleUnpivotTable )
                    // InternalSqlParser.g:1667:3: lv_unpivot_4_0= ruleUnpivotTable
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getTableOrAliasAccess().getUnpivotUnpivotTableParserRuleCall_1_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_35);
                    lv_unpivot_4_0=ruleUnpivotTable();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getTableOrAliasRule());
                      	        }
                             		set(
                             			current, 
                             			"unpivot",
                              		lv_unpivot_4_0, 
                              		"com.jaspersoft.studio.data.Sql.UnpivotTable");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalSqlParser.g:1683:4: ( (lv_alias_5_0= AS ) )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==AS) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalSqlParser.g:1684:1: (lv_alias_5_0= AS )
                    {
                    // InternalSqlParser.g:1684:1: (lv_alias_5_0= AS )
                    // InternalSqlParser.g:1685:3: lv_alias_5_0= AS
                    {
                    lv_alias_5_0=(Token)match(input,AS,FOLLOW_36); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_alias_5_0, grammarAccess.getTableOrAliasAccess().getAliasASKeyword_2_0());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getTableOrAliasRule());
                      	        }
                             		setWithLastConsumed(current, "alias", lv_alias_5_0, "AS");
                      	    
                    }

                    }


                    }
                    break;

            }

            // InternalSqlParser.g:1699:3: ( (lv_tblAlias_6_0= ruleDbObjectName ) )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( ((LA40_0>=RULE_STRING && LA40_0<=RULE_ID)) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalSqlParser.g:1700:1: (lv_tblAlias_6_0= ruleDbObjectName )
                    {
                    // InternalSqlParser.g:1700:1: (lv_tblAlias_6_0= ruleDbObjectName )
                    // InternalSqlParser.g:1701:3: lv_tblAlias_6_0= ruleDbObjectName
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getTableOrAliasAccess().getTblAliasDbObjectNameParserRuleCall_3_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_tblAlias_6_0=ruleDbObjectName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getTableOrAliasRule());
                      	        }
                             		set(
                             			current, 
                             			"tblAlias",
                              		lv_tblAlias_6_0, 
                              		"com.jaspersoft.studio.data.Sql.DbObjectName");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTableOrAlias"


    // $ANTLR start "entryRuleFromValues"
    // InternalSqlParser.g:1725:1: entryRuleFromValues returns [EObject current=null] : iv_ruleFromValues= ruleFromValues EOF ;
    public final EObject entryRuleFromValues() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFromValues = null;


        try {
            // InternalSqlParser.g:1726:2: (iv_ruleFromValues= ruleFromValues EOF )
            // InternalSqlParser.g:1727:2: iv_ruleFromValues= ruleFromValues EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFromValuesRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleFromValues=ruleFromValues();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFromValues; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFromValues"


    // $ANTLR start "ruleFromValues"
    // InternalSqlParser.g:1734:1: ruleFromValues returns [EObject current=null] : ( ( (lv_values_0_0= ruleValues ) ) ( (lv_c_1_0= ruleFromValuesColumns ) )? ) ;
    public final EObject ruleFromValues() throws RecognitionException {
        EObject current = null;

        EObject lv_values_0_0 = null;

        EObject lv_c_1_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:1737:28: ( ( ( (lv_values_0_0= ruleValues ) ) ( (lv_c_1_0= ruleFromValuesColumns ) )? ) )
            // InternalSqlParser.g:1738:1: ( ( (lv_values_0_0= ruleValues ) ) ( (lv_c_1_0= ruleFromValuesColumns ) )? )
            {
            // InternalSqlParser.g:1738:1: ( ( (lv_values_0_0= ruleValues ) ) ( (lv_c_1_0= ruleFromValuesColumns ) )? )
            // InternalSqlParser.g:1738:2: ( (lv_values_0_0= ruleValues ) ) ( (lv_c_1_0= ruleFromValuesColumns ) )?
            {
            // InternalSqlParser.g:1738:2: ( (lv_values_0_0= ruleValues ) )
            // InternalSqlParser.g:1739:1: (lv_values_0_0= ruleValues )
            {
            // InternalSqlParser.g:1739:1: (lv_values_0_0= ruleValues )
            // InternalSqlParser.g:1740:3: lv_values_0_0= ruleValues
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getFromValuesAccess().getValuesValuesParserRuleCall_0_0()); 
              	    
            }
            pushFollow(FOLLOW_41);
            lv_values_0_0=ruleValues();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getFromValuesRule());
              	        }
                     		set(
                     			current, 
                     			"values",
                      		lv_values_0_0, 
                      		"com.jaspersoft.studio.data.Sql.Values");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:1756:2: ( (lv_c_1_0= ruleFromValuesColumns ) )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==RULE_ID) ) {
                int LA41_1 = input.LA(2);

                if ( (LA41_1==LeftParenthesis) ) {
                    alt41=1;
                }
            }
            switch (alt41) {
                case 1 :
                    // InternalSqlParser.g:1757:1: (lv_c_1_0= ruleFromValuesColumns )
                    {
                    // InternalSqlParser.g:1757:1: (lv_c_1_0= ruleFromValuesColumns )
                    // InternalSqlParser.g:1758:3: lv_c_1_0= ruleFromValuesColumns
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getFromValuesAccess().getCFromValuesColumnsParserRuleCall_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_c_1_0=ruleFromValuesColumns();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getFromValuesRule());
                      	        }
                             		set(
                             			current, 
                             			"c",
                              		lv_c_1_0, 
                              		"com.jaspersoft.studio.data.Sql.FromValuesColumns");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFromValues"


    // $ANTLR start "entryRuleFromValuesColumns"
    // InternalSqlParser.g:1782:1: entryRuleFromValuesColumns returns [EObject current=null] : iv_ruleFromValuesColumns= ruleFromValuesColumns EOF ;
    public final EObject entryRuleFromValuesColumns() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFromValuesColumns = null;


        try {
            // InternalSqlParser.g:1783:2: (iv_ruleFromValuesColumns= ruleFromValuesColumns EOF )
            // InternalSqlParser.g:1784:2: iv_ruleFromValuesColumns= ruleFromValuesColumns EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFromValuesColumnsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleFromValuesColumns=ruleFromValuesColumns();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFromValuesColumns; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFromValuesColumns"


    // $ANTLR start "ruleFromValuesColumns"
    // InternalSqlParser.g:1791:1: ruleFromValuesColumns returns [EObject current=null] : (this_ID_0= RULE_ID otherlv_1= LeftParenthesis ( (lv_fvCols_2_0= ruleFromValuesColumnNames ) ) otherlv_3= RightParenthesis ) ;
    public final EObject ruleFromValuesColumns() throws RecognitionException {
        EObject current = null;

        Token this_ID_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_fvCols_2_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:1794:28: ( (this_ID_0= RULE_ID otherlv_1= LeftParenthesis ( (lv_fvCols_2_0= ruleFromValuesColumnNames ) ) otherlv_3= RightParenthesis ) )
            // InternalSqlParser.g:1795:1: (this_ID_0= RULE_ID otherlv_1= LeftParenthesis ( (lv_fvCols_2_0= ruleFromValuesColumnNames ) ) otherlv_3= RightParenthesis )
            {
            // InternalSqlParser.g:1795:1: (this_ID_0= RULE_ID otherlv_1= LeftParenthesis ( (lv_fvCols_2_0= ruleFromValuesColumnNames ) ) otherlv_3= RightParenthesis )
            // InternalSqlParser.g:1795:2: this_ID_0= RULE_ID otherlv_1= LeftParenthesis ( (lv_fvCols_2_0= ruleFromValuesColumnNames ) ) otherlv_3= RightParenthesis
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                  newLeafNode(this_ID_0, grammarAccess.getFromValuesColumnsAccess().getIDTerminalRuleCall_0()); 
                  
            }
            otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_42); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getFromValuesColumnsAccess().getLeftParenthesisKeyword_1());
                  
            }
            // InternalSqlParser.g:1804:1: ( (lv_fvCols_2_0= ruleFromValuesColumnNames ) )
            // InternalSqlParser.g:1805:1: (lv_fvCols_2_0= ruleFromValuesColumnNames )
            {
            // InternalSqlParser.g:1805:1: (lv_fvCols_2_0= ruleFromValuesColumnNames )
            // InternalSqlParser.g:1806:3: lv_fvCols_2_0= ruleFromValuesColumnNames
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getFromValuesColumnsAccess().getFvColsFromValuesColumnNamesParserRuleCall_2_0()); 
              	    
            }
            pushFollow(FOLLOW_8);
            lv_fvCols_2_0=ruleFromValuesColumnNames();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getFromValuesColumnsRule());
              	        }
                     		set(
                     			current, 
                     			"fvCols",
                      		lv_fvCols_2_0, 
                      		"com.jaspersoft.studio.data.Sql.FromValuesColumnNames");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_3=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getFromValuesColumnsAccess().getRightParenthesisKeyword_3());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFromValuesColumns"


    // $ANTLR start "entryRuleFromValuesColumnNames"
    // InternalSqlParser.g:1835:1: entryRuleFromValuesColumnNames returns [EObject current=null] : iv_ruleFromValuesColumnNames= ruleFromValuesColumnNames EOF ;
    public final EObject entryRuleFromValuesColumnNames() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFromValuesColumnNames = null;


        try {
            // InternalSqlParser.g:1836:2: (iv_ruleFromValuesColumnNames= ruleFromValuesColumnNames EOF )
            // InternalSqlParser.g:1837:2: iv_ruleFromValuesColumnNames= ruleFromValuesColumnNames EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFromValuesColumnNamesRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleFromValuesColumnNames=ruleFromValuesColumnNames();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFromValuesColumnNames; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFromValuesColumnNames"


    // $ANTLR start "ruleFromValuesColumnNames"
    // InternalSqlParser.g:1844:1: ruleFromValuesColumnNames returns [EObject current=null] : (this_ColumnName_0= ruleColumnName ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleColumnName ) ) )+ )? ) ;
    public final EObject ruleFromValuesColumnNames() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_ColumnName_0 = null;

        EObject lv_entries_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:1847:28: ( (this_ColumnName_0= ruleColumnName ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleColumnName ) ) )+ )? ) )
            // InternalSqlParser.g:1848:1: (this_ColumnName_0= ruleColumnName ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleColumnName ) ) )+ )? )
            {
            // InternalSqlParser.g:1848:1: (this_ColumnName_0= ruleColumnName ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleColumnName ) ) )+ )? )
            // InternalSqlParser.g:1849:2: this_ColumnName_0= ruleColumnName ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleColumnName ) ) )+ )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getFromValuesColumnNamesAccess().getColumnNameParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_9);
            this_ColumnName_0=ruleColumnName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_ColumnName_0;
                      afterParserOrEnumRuleCall();
                  
            }
            // InternalSqlParser.g:1860:1: ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleColumnName ) ) )+ )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==Comma) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // InternalSqlParser.g:1860:2: () (otherlv_2= Comma ( (lv_entries_3_0= ruleColumnName ) ) )+
                    {
                    // InternalSqlParser.g:1860:2: ()
                    // InternalSqlParser.g:1861:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getFromValuesColumnNamesAccess().getAbcEntriesAction_1_0(),
                                  current);
                          
                    }

                    }

                    // InternalSqlParser.g:1869:2: (otherlv_2= Comma ( (lv_entries_3_0= ruleColumnName ) ) )+
                    int cnt42=0;
                    loop42:
                    do {
                        int alt42=2;
                        int LA42_0 = input.LA(1);

                        if ( (LA42_0==Comma) ) {
                            alt42=1;
                        }


                        switch (alt42) {
                    	case 1 :
                    	    // InternalSqlParser.g:1870:2: otherlv_2= Comma ( (lv_entries_3_0= ruleColumnName ) )
                    	    {
                    	    otherlv_2=(Token)match(input,Comma,FOLLOW_42); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_2, grammarAccess.getFromValuesColumnNamesAccess().getCommaKeyword_1_1_0());
                    	          
                    	    }
                    	    // InternalSqlParser.g:1874:1: ( (lv_entries_3_0= ruleColumnName ) )
                    	    // InternalSqlParser.g:1875:1: (lv_entries_3_0= ruleColumnName )
                    	    {
                    	    // InternalSqlParser.g:1875:1: (lv_entries_3_0= ruleColumnName )
                    	    // InternalSqlParser.g:1876:3: lv_entries_3_0= ruleColumnName
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getFromValuesColumnNamesAccess().getEntriesColumnNameParserRuleCall_1_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_9);
                    	    lv_entries_3_0=ruleColumnName();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getFromValuesColumnNamesRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"entries",
                    	              		lv_entries_3_0, 
                    	              		"com.jaspersoft.studio.data.Sql.ColumnName");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt42 >= 1 ) break loop42;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(42, input);
                                throw eee;
                        }
                        cnt42++;
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFromValuesColumnNames"


    // $ANTLR start "entryRuleColumnName"
    // InternalSqlParser.g:1900:1: entryRuleColumnName returns [EObject current=null] : iv_ruleColumnName= ruleColumnName EOF ;
    public final EObject entryRuleColumnName() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleColumnName = null;


        try {
            // InternalSqlParser.g:1901:2: (iv_ruleColumnName= ruleColumnName EOF )
            // InternalSqlParser.g:1902:2: iv_ruleColumnName= ruleColumnName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getColumnNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleColumnName=ruleColumnName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleColumnName; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleColumnName"


    // $ANTLR start "ruleColumnName"
    // InternalSqlParser.g:1909:1: ruleColumnName returns [EObject current=null] : ( (lv_colName_0_0= RULE_STRING ) ) ;
    public final EObject ruleColumnName() throws RecognitionException {
        EObject current = null;

        Token lv_colName_0_0=null;

         enterRule(); 
            
        try {
            // InternalSqlParser.g:1912:28: ( ( (lv_colName_0_0= RULE_STRING ) ) )
            // InternalSqlParser.g:1913:1: ( (lv_colName_0_0= RULE_STRING ) )
            {
            // InternalSqlParser.g:1913:1: ( (lv_colName_0_0= RULE_STRING ) )
            // InternalSqlParser.g:1914:1: (lv_colName_0_0= RULE_STRING )
            {
            // InternalSqlParser.g:1914:1: (lv_colName_0_0= RULE_STRING )
            // InternalSqlParser.g:1915:3: lv_colName_0_0= RULE_STRING
            {
            lv_colName_0_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_colName_0_0, grammarAccess.getColumnNameAccess().getColNameSTRINGTerminalRuleCall_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getColumnNameRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"colName",
                      		lv_colName_0_0, 
                      		"com.jaspersoft.studio.data.Sql.STRING");
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleColumnName"


    // $ANTLR start "entryRuleValues"
    // InternalSqlParser.g:1939:1: entryRuleValues returns [EObject current=null] : iv_ruleValues= ruleValues EOF ;
    public final EObject entryRuleValues() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValues = null;


        try {
            // InternalSqlParser.g:1940:2: (iv_ruleValues= ruleValues EOF )
            // InternalSqlParser.g:1941:2: iv_ruleValues= ruleValues EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getValuesRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleValues=ruleValues();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleValues; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleValues"


    // $ANTLR start "ruleValues"
    // InternalSqlParser.g:1948:1: ruleValues returns [EObject current=null] : (otherlv_0= LeftParenthesis otherlv_1= VALUES ( (lv_rows_2_0= ruleRows ) ) otherlv_3= RightParenthesis ) ;
    public final EObject ruleValues() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_rows_2_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:1951:28: ( (otherlv_0= LeftParenthesis otherlv_1= VALUES ( (lv_rows_2_0= ruleRows ) ) otherlv_3= RightParenthesis ) )
            // InternalSqlParser.g:1952:1: (otherlv_0= LeftParenthesis otherlv_1= VALUES ( (lv_rows_2_0= ruleRows ) ) otherlv_3= RightParenthesis )
            {
            // InternalSqlParser.g:1952:1: (otherlv_0= LeftParenthesis otherlv_1= VALUES ( (lv_rows_2_0= ruleRows ) ) otherlv_3= RightParenthesis )
            // InternalSqlParser.g:1953:2: otherlv_0= LeftParenthesis otherlv_1= VALUES ( (lv_rows_2_0= ruleRows ) ) otherlv_3= RightParenthesis
            {
            otherlv_0=(Token)match(input,LeftParenthesis,FOLLOW_43); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getValuesAccess().getLeftParenthesisKeyword_0());
                  
            }
            otherlv_1=(Token)match(input,VALUES,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getValuesAccess().getVALUESKeyword_1());
                  
            }
            // InternalSqlParser.g:1962:1: ( (lv_rows_2_0= ruleRows ) )
            // InternalSqlParser.g:1963:1: (lv_rows_2_0= ruleRows )
            {
            // InternalSqlParser.g:1963:1: (lv_rows_2_0= ruleRows )
            // InternalSqlParser.g:1964:3: lv_rows_2_0= ruleRows
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getValuesAccess().getRowsRowsParserRuleCall_2_0()); 
              	    
            }
            pushFollow(FOLLOW_8);
            lv_rows_2_0=ruleRows();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getValuesRule());
              	        }
                     		set(
                     			current, 
                     			"rows",
                      		lv_rows_2_0, 
                      		"com.jaspersoft.studio.data.Sql.Rows");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_3=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getValuesAccess().getRightParenthesisKeyword_3());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleValues"


    // $ANTLR start "entryRuleRows"
    // InternalSqlParser.g:1993:1: entryRuleRows returns [EObject current=null] : iv_ruleRows= ruleRows EOF ;
    public final EObject entryRuleRows() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRows = null;


        try {
            // InternalSqlParser.g:1994:2: (iv_ruleRows= ruleRows EOF )
            // InternalSqlParser.g:1995:2: iv_ruleRows= ruleRows EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getRowsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleRows=ruleRows();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleRows; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRows"


    // $ANTLR start "ruleRows"
    // InternalSqlParser.g:2002:1: ruleRows returns [EObject current=null] : (this_Row_0= ruleRow ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleRow ) ) )+ )? ) ;
    public final EObject ruleRows() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_Row_0 = null;

        EObject lv_entries_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:2005:28: ( (this_Row_0= ruleRow ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleRow ) ) )+ )? ) )
            // InternalSqlParser.g:2006:1: (this_Row_0= ruleRow ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleRow ) ) )+ )? )
            {
            // InternalSqlParser.g:2006:1: (this_Row_0= ruleRow ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleRow ) ) )+ )? )
            // InternalSqlParser.g:2007:2: this_Row_0= ruleRow ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleRow ) ) )+ )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getRowsAccess().getRowParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_9);
            this_Row_0=ruleRow();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_Row_0;
                      afterParserOrEnumRuleCall();
                  
            }
            // InternalSqlParser.g:2018:1: ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleRow ) ) )+ )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==Comma) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // InternalSqlParser.g:2018:2: () (otherlv_2= Comma ( (lv_entries_3_0= ruleRow ) ) )+
                    {
                    // InternalSqlParser.g:2018:2: ()
                    // InternalSqlParser.g:2019:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getRowsAccess().getRowsEntriesAction_1_0(),
                                  current);
                          
                    }

                    }

                    // InternalSqlParser.g:2027:2: (otherlv_2= Comma ( (lv_entries_3_0= ruleRow ) ) )+
                    int cnt44=0;
                    loop44:
                    do {
                        int alt44=2;
                        int LA44_0 = input.LA(1);

                        if ( (LA44_0==Comma) ) {
                            alt44=1;
                        }


                        switch (alt44) {
                    	case 1 :
                    	    // InternalSqlParser.g:2028:2: otherlv_2= Comma ( (lv_entries_3_0= ruleRow ) )
                    	    {
                    	    otherlv_2=(Token)match(input,Comma,FOLLOW_7); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_2, grammarAccess.getRowsAccess().getCommaKeyword_1_1_0());
                    	          
                    	    }
                    	    // InternalSqlParser.g:2032:1: ( (lv_entries_3_0= ruleRow ) )
                    	    // InternalSqlParser.g:2033:1: (lv_entries_3_0= ruleRow )
                    	    {
                    	    // InternalSqlParser.g:2033:1: (lv_entries_3_0= ruleRow )
                    	    // InternalSqlParser.g:2034:3: lv_entries_3_0= ruleRow
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getRowsAccess().getEntriesRowParserRuleCall_1_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_9);
                    	    lv_entries_3_0=ruleRow();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getRowsRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"entries",
                    	              		lv_entries_3_0, 
                    	              		"com.jaspersoft.studio.data.Sql.Row");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt44 >= 1 ) break loop44;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(44, input);
                                throw eee;
                        }
                        cnt44++;
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRows"


    // $ANTLR start "entryRuleRow"
    // InternalSqlParser.g:2058:1: entryRuleRow returns [EObject current=null] : iv_ruleRow= ruleRow EOF ;
    public final EObject entryRuleRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRow = null;


        try {
            // InternalSqlParser.g:2059:2: (iv_ruleRow= ruleRow EOF )
            // InternalSqlParser.g:2060:2: iv_ruleRow= ruleRow EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getRowRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleRow=ruleRow();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleRow; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRow"


    // $ANTLR start "ruleRow"
    // InternalSqlParser.g:2067:1: ruleRow returns [EObject current=null] : (otherlv_0= LeftParenthesis ( (lv_rowValues_1_0= ruleRowValues ) ) otherlv_2= RightParenthesis ) ;
    public final EObject ruleRow() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_rowValues_1_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:2070:28: ( (otherlv_0= LeftParenthesis ( (lv_rowValues_1_0= ruleRowValues ) ) otherlv_2= RightParenthesis ) )
            // InternalSqlParser.g:2071:1: (otherlv_0= LeftParenthesis ( (lv_rowValues_1_0= ruleRowValues ) ) otherlv_2= RightParenthesis )
            {
            // InternalSqlParser.g:2071:1: (otherlv_0= LeftParenthesis ( (lv_rowValues_1_0= ruleRowValues ) ) otherlv_2= RightParenthesis )
            // InternalSqlParser.g:2072:2: otherlv_0= LeftParenthesis ( (lv_rowValues_1_0= ruleRowValues ) ) otherlv_2= RightParenthesis
            {
            otherlv_0=(Token)match(input,LeftParenthesis,FOLLOW_44); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getRowAccess().getLeftParenthesisKeyword_0());
                  
            }
            // InternalSqlParser.g:2076:1: ( (lv_rowValues_1_0= ruleRowValues ) )
            // InternalSqlParser.g:2077:1: (lv_rowValues_1_0= ruleRowValues )
            {
            // InternalSqlParser.g:2077:1: (lv_rowValues_1_0= ruleRowValues )
            // InternalSqlParser.g:2078:3: lv_rowValues_1_0= ruleRowValues
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getRowAccess().getRowValuesRowValuesParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_8);
            lv_rowValues_1_0=ruleRowValues();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getRowRule());
              	        }
                     		set(
                     			current, 
                     			"rowValues",
                      		lv_rowValues_1_0, 
                      		"com.jaspersoft.studio.data.Sql.RowValues");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_2=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getRowAccess().getRightParenthesisKeyword_2());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRow"


    // $ANTLR start "entryRuleRowValues"
    // InternalSqlParser.g:2107:1: entryRuleRowValues returns [EObject current=null] : iv_ruleRowValues= ruleRowValues EOF ;
    public final EObject entryRuleRowValues() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRowValues = null;


        try {
            // InternalSqlParser.g:2108:2: (iv_ruleRowValues= ruleRowValues EOF )
            // InternalSqlParser.g:2109:2: iv_ruleRowValues= ruleRowValues EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getRowValuesRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleRowValues=ruleRowValues();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleRowValues; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRowValues"


    // $ANTLR start "ruleRowValues"
    // InternalSqlParser.g:2116:1: ruleRowValues returns [EObject current=null] : (this_RowValue_0= ruleRowValue ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleRowValue ) ) )+ )? ) ;
    public final EObject ruleRowValues() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_RowValue_0 = null;

        EObject lv_entries_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:2119:28: ( (this_RowValue_0= ruleRowValue ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleRowValue ) ) )+ )? ) )
            // InternalSqlParser.g:2120:1: (this_RowValue_0= ruleRowValue ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleRowValue ) ) )+ )? )
            {
            // InternalSqlParser.g:2120:1: (this_RowValue_0= ruleRowValue ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleRowValue ) ) )+ )? )
            // InternalSqlParser.g:2121:2: this_RowValue_0= ruleRowValue ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleRowValue ) ) )+ )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getRowValuesAccess().getRowValueParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_9);
            this_RowValue_0=ruleRowValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_RowValue_0;
                      afterParserOrEnumRuleCall();
                  
            }
            // InternalSqlParser.g:2132:1: ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleRowValue ) ) )+ )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==Comma) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // InternalSqlParser.g:2132:2: () (otherlv_2= Comma ( (lv_entries_3_0= ruleRowValue ) ) )+
                    {
                    // InternalSqlParser.g:2132:2: ()
                    // InternalSqlParser.g:2133:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getRowValuesAccess().getRowValuesEntriesAction_1_0(),
                                  current);
                          
                    }

                    }

                    // InternalSqlParser.g:2141:2: (otherlv_2= Comma ( (lv_entries_3_0= ruleRowValue ) ) )+
                    int cnt46=0;
                    loop46:
                    do {
                        int alt46=2;
                        int LA46_0 = input.LA(1);

                        if ( (LA46_0==Comma) ) {
                            alt46=1;
                        }


                        switch (alt46) {
                    	case 1 :
                    	    // InternalSqlParser.g:2142:2: otherlv_2= Comma ( (lv_entries_3_0= ruleRowValue ) )
                    	    {
                    	    otherlv_2=(Token)match(input,Comma,FOLLOW_44); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_2, grammarAccess.getRowValuesAccess().getCommaKeyword_1_1_0());
                    	          
                    	    }
                    	    // InternalSqlParser.g:2146:1: ( (lv_entries_3_0= ruleRowValue ) )
                    	    // InternalSqlParser.g:2147:1: (lv_entries_3_0= ruleRowValue )
                    	    {
                    	    // InternalSqlParser.g:2147:1: (lv_entries_3_0= ruleRowValue )
                    	    // InternalSqlParser.g:2148:3: lv_entries_3_0= ruleRowValue
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getRowValuesAccess().getEntriesRowValueParserRuleCall_1_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_9);
                    	    lv_entries_3_0=ruleRowValue();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getRowValuesRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"entries",
                    	              		lv_entries_3_0, 
                    	              		"com.jaspersoft.studio.data.Sql.RowValue");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt46 >= 1 ) break loop46;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(46, input);
                                throw eee;
                        }
                        cnt46++;
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRowValues"


    // $ANTLR start "entryRuleRowValue"
    // InternalSqlParser.g:2172:1: entryRuleRowValue returns [EObject current=null] : iv_ruleRowValue= ruleRowValue EOF ;
    public final EObject entryRuleRowValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRowValue = null;


        try {
            // InternalSqlParser.g:2173:2: (iv_ruleRowValue= ruleRowValue EOF )
            // InternalSqlParser.g:2174:2: iv_ruleRowValue= ruleRowValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getRowValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleRowValue=ruleRowValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleRowValue; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRowValue"


    // $ANTLR start "ruleRowValue"
    // InternalSqlParser.g:2181:1: ruleRowValue returns [EObject current=null] : (this_ScalarNumberOperand_0= ruleScalarNumberOperand | ( (lv_null_1_0= NULL ) ) ) ;
    public final EObject ruleRowValue() throws RecognitionException {
        EObject current = null;

        Token lv_null_1_0=null;
        EObject this_ScalarNumberOperand_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:2184:28: ( (this_ScalarNumberOperand_0= ruleScalarNumberOperand | ( (lv_null_1_0= NULL ) ) ) )
            // InternalSqlParser.g:2185:1: (this_ScalarNumberOperand_0= ruleScalarNumberOperand | ( (lv_null_1_0= NULL ) ) )
            {
            // InternalSqlParser.g:2185:1: (this_ScalarNumberOperand_0= ruleScalarNumberOperand | ( (lv_null_1_0= NULL ) ) )
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( ((LA48_0>=RULE_UNSIGNED && LA48_0<=RULE_SIGNED_DOUBLE)||LA48_0==RULE_STRING_) ) {
                alt48=1;
            }
            else if ( (LA48_0==NULL) ) {
                alt48=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 48, 0, input);

                throw nvae;
            }
            switch (alt48) {
                case 1 :
                    // InternalSqlParser.g:2186:2: this_ScalarNumberOperand_0= ruleScalarNumberOperand
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getRowValueAccess().getScalarNumberOperandParserRuleCall_0()); 
                          
                    }
                    pushFollow(FOLLOW_2);
                    this_ScalarNumberOperand_0=ruleScalarNumberOperand();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_ScalarNumberOperand_0;
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:2198:6: ( (lv_null_1_0= NULL ) )
                    {
                    // InternalSqlParser.g:2198:6: ( (lv_null_1_0= NULL ) )
                    // InternalSqlParser.g:2199:1: (lv_null_1_0= NULL )
                    {
                    // InternalSqlParser.g:2199:1: (lv_null_1_0= NULL )
                    // InternalSqlParser.g:2200:3: lv_null_1_0= NULL
                    {
                    lv_null_1_0=(Token)match(input,NULL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_null_1_0, grammarAccess.getRowValueAccess().getNullNULLKeyword_1_0());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getRowValueRule());
                      	        }
                             		setWithLastConsumed(current, "null", lv_null_1_0, "NULL");
                      	    
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRowValue"


    // $ANTLR start "entryRulePivotTable"
    // InternalSqlParser.g:2222:1: entryRulePivotTable returns [EObject current=null] : iv_rulePivotTable= rulePivotTable EOF ;
    public final EObject entryRulePivotTable() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePivotTable = null;


        try {
            // InternalSqlParser.g:2223:2: (iv_rulePivotTable= rulePivotTable EOF )
            // InternalSqlParser.g:2224:2: iv_rulePivotTable= rulePivotTable EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPivotTableRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePivotTable=rulePivotTable();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePivotTable; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePivotTable"


    // $ANTLR start "rulePivotTable"
    // InternalSqlParser.g:2231:1: rulePivotTable returns [EObject current=null] : (otherlv_0= PIVOT (otherlv_1= XML )? otherlv_2= LeftParenthesis ( (lv_pfun_3_0= rulePivotFunctions ) ) ( (lv_pfor_4_0= rulePivotForClause ) ) ( (lv_pin_5_0= rulePivotInClause ) ) otherlv_6= RightParenthesis ) ;
    public final EObject rulePivotTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_6=null;
        EObject lv_pfun_3_0 = null;

        EObject lv_pfor_4_0 = null;

        EObject lv_pin_5_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:2234:28: ( (otherlv_0= PIVOT (otherlv_1= XML )? otherlv_2= LeftParenthesis ( (lv_pfun_3_0= rulePivotFunctions ) ) ( (lv_pfor_4_0= rulePivotForClause ) ) ( (lv_pin_5_0= rulePivotInClause ) ) otherlv_6= RightParenthesis ) )
            // InternalSqlParser.g:2235:1: (otherlv_0= PIVOT (otherlv_1= XML )? otherlv_2= LeftParenthesis ( (lv_pfun_3_0= rulePivotFunctions ) ) ( (lv_pfor_4_0= rulePivotForClause ) ) ( (lv_pin_5_0= rulePivotInClause ) ) otherlv_6= RightParenthesis )
            {
            // InternalSqlParser.g:2235:1: (otherlv_0= PIVOT (otherlv_1= XML )? otherlv_2= LeftParenthesis ( (lv_pfun_3_0= rulePivotFunctions ) ) ( (lv_pfor_4_0= rulePivotForClause ) ) ( (lv_pin_5_0= rulePivotInClause ) ) otherlv_6= RightParenthesis )
            // InternalSqlParser.g:2236:2: otherlv_0= PIVOT (otherlv_1= XML )? otherlv_2= LeftParenthesis ( (lv_pfun_3_0= rulePivotFunctions ) ) ( (lv_pfor_4_0= rulePivotForClause ) ) ( (lv_pin_5_0= rulePivotInClause ) ) otherlv_6= RightParenthesis
            {
            otherlv_0=(Token)match(input,PIVOT,FOLLOW_45); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getPivotTableAccess().getPIVOTKeyword_0());
                  
            }
            // InternalSqlParser.g:2240:1: (otherlv_1= XML )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==XML) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // InternalSqlParser.g:2241:2: otherlv_1= XML
                    {
                    otherlv_1=(Token)match(input,XML,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_1, grammarAccess.getPivotTableAccess().getXMLKeyword_1());
                          
                    }

                    }
                    break;

            }

            otherlv_2=(Token)match(input,LeftParenthesis,FOLLOW_46); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getPivotTableAccess().getLeftParenthesisKeyword_2());
                  
            }
            // InternalSqlParser.g:2250:1: ( (lv_pfun_3_0= rulePivotFunctions ) )
            // InternalSqlParser.g:2251:1: (lv_pfun_3_0= rulePivotFunctions )
            {
            // InternalSqlParser.g:2251:1: (lv_pfun_3_0= rulePivotFunctions )
            // InternalSqlParser.g:2252:3: lv_pfun_3_0= rulePivotFunctions
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getPivotTableAccess().getPfunPivotFunctionsParserRuleCall_3_0()); 
              	    
            }
            pushFollow(FOLLOW_47);
            lv_pfun_3_0=rulePivotFunctions();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getPivotTableRule());
              	        }
                     		set(
                     			current, 
                     			"pfun",
                      		lv_pfun_3_0, 
                      		"com.jaspersoft.studio.data.Sql.PivotFunctions");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:2268:2: ( (lv_pfor_4_0= rulePivotForClause ) )
            // InternalSqlParser.g:2269:1: (lv_pfor_4_0= rulePivotForClause )
            {
            // InternalSqlParser.g:2269:1: (lv_pfor_4_0= rulePivotForClause )
            // InternalSqlParser.g:2270:3: lv_pfor_4_0= rulePivotForClause
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getPivotTableAccess().getPforPivotForClauseParserRuleCall_4_0()); 
              	    
            }
            pushFollow(FOLLOW_48);
            lv_pfor_4_0=rulePivotForClause();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getPivotTableRule());
              	        }
                     		set(
                     			current, 
                     			"pfor",
                      		lv_pfor_4_0, 
                      		"com.jaspersoft.studio.data.Sql.PivotForClause");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:2286:2: ( (lv_pin_5_0= rulePivotInClause ) )
            // InternalSqlParser.g:2287:1: (lv_pin_5_0= rulePivotInClause )
            {
            // InternalSqlParser.g:2287:1: (lv_pin_5_0= rulePivotInClause )
            // InternalSqlParser.g:2288:3: lv_pin_5_0= rulePivotInClause
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getPivotTableAccess().getPinPivotInClauseParserRuleCall_5_0()); 
              	    
            }
            pushFollow(FOLLOW_8);
            lv_pin_5_0=rulePivotInClause();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getPivotTableRule());
              	        }
                     		set(
                     			current, 
                     			"pin",
                      		lv_pin_5_0, 
                      		"com.jaspersoft.studio.data.Sql.PivotInClause");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_6=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_6, grammarAccess.getPivotTableAccess().getRightParenthesisKeyword_6());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePivotTable"


    // $ANTLR start "entryRulePivotFunctions"
    // InternalSqlParser.g:2317:1: entryRulePivotFunctions returns [EObject current=null] : iv_rulePivotFunctions= rulePivotFunctions EOF ;
    public final EObject entryRulePivotFunctions() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePivotFunctions = null;


        try {
            // InternalSqlParser.g:2318:2: (iv_rulePivotFunctions= rulePivotFunctions EOF )
            // InternalSqlParser.g:2319:2: iv_rulePivotFunctions= rulePivotFunctions EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPivotFunctionsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePivotFunctions=rulePivotFunctions();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePivotFunctions; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePivotFunctions"


    // $ANTLR start "rulePivotFunctions"
    // InternalSqlParser.g:2326:1: rulePivotFunctions returns [EObject current=null] : ( (lv_abc_0_0= RULE_ID ) ) ;
    public final EObject rulePivotFunctions() throws RecognitionException {
        EObject current = null;

        Token lv_abc_0_0=null;

         enterRule(); 
            
        try {
            // InternalSqlParser.g:2329:28: ( ( (lv_abc_0_0= RULE_ID ) ) )
            // InternalSqlParser.g:2330:1: ( (lv_abc_0_0= RULE_ID ) )
            {
            // InternalSqlParser.g:2330:1: ( (lv_abc_0_0= RULE_ID ) )
            // InternalSqlParser.g:2331:1: (lv_abc_0_0= RULE_ID )
            {
            // InternalSqlParser.g:2331:1: (lv_abc_0_0= RULE_ID )
            // InternalSqlParser.g:2332:3: lv_abc_0_0= RULE_ID
            {
            lv_abc_0_0=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_abc_0_0, grammarAccess.getPivotFunctionsAccess().getAbcIDTerminalRuleCall_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getPivotFunctionsRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"abc",
                      		lv_abc_0_0, 
                      		"com.jaspersoft.studio.data.Sql.ID");
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePivotFunctions"


    // $ANTLR start "entryRulePivotInClause"
    // InternalSqlParser.g:2358:1: entryRulePivotInClause returns [EObject current=null] : iv_rulePivotInClause= rulePivotInClause EOF ;
    public final EObject entryRulePivotInClause() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePivotInClause = null;


        try {
            // InternalSqlParser.g:2359:2: (iv_rulePivotInClause= rulePivotInClause EOF )
            // InternalSqlParser.g:2360:2: iv_rulePivotInClause= rulePivotInClause EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPivotInClauseRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePivotInClause=rulePivotInClause();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePivotInClause; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePivotInClause"


    // $ANTLR start "rulePivotInClause"
    // InternalSqlParser.g:2367:1: rulePivotInClause returns [EObject current=null] : (otherlv_0= IN otherlv_1= LeftParenthesis ( ( (lv_sq_2_0= ruleSubQueryOperand ) ) | ( (lv_args_3_0= ruleUnpivotInClauseArgs ) ) | ( (lv_pinany_4_0= rulePivotInClauseAny ) ) ) otherlv_5= RightParenthesis ) ;
    public final EObject rulePivotInClause() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_5=null;
        EObject lv_sq_2_0 = null;

        EObject lv_args_3_0 = null;

        AntlrDatatypeRuleToken lv_pinany_4_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:2370:28: ( (otherlv_0= IN otherlv_1= LeftParenthesis ( ( (lv_sq_2_0= ruleSubQueryOperand ) ) | ( (lv_args_3_0= ruleUnpivotInClauseArgs ) ) | ( (lv_pinany_4_0= rulePivotInClauseAny ) ) ) otherlv_5= RightParenthesis ) )
            // InternalSqlParser.g:2371:1: (otherlv_0= IN otherlv_1= LeftParenthesis ( ( (lv_sq_2_0= ruleSubQueryOperand ) ) | ( (lv_args_3_0= ruleUnpivotInClauseArgs ) ) | ( (lv_pinany_4_0= rulePivotInClauseAny ) ) ) otherlv_5= RightParenthesis )
            {
            // InternalSqlParser.g:2371:1: (otherlv_0= IN otherlv_1= LeftParenthesis ( ( (lv_sq_2_0= ruleSubQueryOperand ) ) | ( (lv_args_3_0= ruleUnpivotInClauseArgs ) ) | ( (lv_pinany_4_0= rulePivotInClauseAny ) ) ) otherlv_5= RightParenthesis )
            // InternalSqlParser.g:2372:2: otherlv_0= IN otherlv_1= LeftParenthesis ( ( (lv_sq_2_0= ruleSubQueryOperand ) ) | ( (lv_args_3_0= ruleUnpivotInClauseArgs ) ) | ( (lv_pinany_4_0= rulePivotInClauseAny ) ) ) otherlv_5= RightParenthesis
            {
            otherlv_0=(Token)match(input,IN,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getPivotInClauseAccess().getINKeyword_0());
                  
            }
            otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_49); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getPivotInClauseAccess().getLeftParenthesisKeyword_1());
                  
            }
            // InternalSqlParser.g:2381:1: ( ( (lv_sq_2_0= ruleSubQueryOperand ) ) | ( (lv_args_3_0= ruleUnpivotInClauseArgs ) ) | ( (lv_pinany_4_0= rulePivotInClauseAny ) ) )
            int alt50=3;
            switch ( input.LA(1) ) {
            case LeftParenthesis:
                {
                int LA50_1 = input.LA(2);

                if ( ((LA50_1>=RULE_STRING && LA50_1<=RULE_ID)) ) {
                    alt50=2;
                }
                else if ( (LA50_1==SELECT) ) {
                    alt50=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 50, 1, input);

                    throw nvae;
                }
                }
                break;
            case RULE_STRING:
            case RULE_DBNAME:
            case RULE_ID:
                {
                alt50=2;
                }
                break;
            case ANY:
                {
                alt50=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 50, 0, input);

                throw nvae;
            }

            switch (alt50) {
                case 1 :
                    // InternalSqlParser.g:2381:2: ( (lv_sq_2_0= ruleSubQueryOperand ) )
                    {
                    // InternalSqlParser.g:2381:2: ( (lv_sq_2_0= ruleSubQueryOperand ) )
                    // InternalSqlParser.g:2382:1: (lv_sq_2_0= ruleSubQueryOperand )
                    {
                    // InternalSqlParser.g:2382:1: (lv_sq_2_0= ruleSubQueryOperand )
                    // InternalSqlParser.g:2383:3: lv_sq_2_0= ruleSubQueryOperand
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getPivotInClauseAccess().getSqSubQueryOperandParserRuleCall_2_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_8);
                    lv_sq_2_0=ruleSubQueryOperand();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getPivotInClauseRule());
                      	        }
                             		set(
                             			current, 
                             			"sq",
                              		lv_sq_2_0, 
                              		"com.jaspersoft.studio.data.Sql.SubQueryOperand");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:2400:6: ( (lv_args_3_0= ruleUnpivotInClauseArgs ) )
                    {
                    // InternalSqlParser.g:2400:6: ( (lv_args_3_0= ruleUnpivotInClauseArgs ) )
                    // InternalSqlParser.g:2401:1: (lv_args_3_0= ruleUnpivotInClauseArgs )
                    {
                    // InternalSqlParser.g:2401:1: (lv_args_3_0= ruleUnpivotInClauseArgs )
                    // InternalSqlParser.g:2402:3: lv_args_3_0= ruleUnpivotInClauseArgs
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getPivotInClauseAccess().getArgsUnpivotInClauseArgsParserRuleCall_2_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_8);
                    lv_args_3_0=ruleUnpivotInClauseArgs();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getPivotInClauseRule());
                      	        }
                             		set(
                             			current, 
                             			"args",
                              		lv_args_3_0, 
                              		"com.jaspersoft.studio.data.Sql.UnpivotInClauseArgs");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSqlParser.g:2419:6: ( (lv_pinany_4_0= rulePivotInClauseAny ) )
                    {
                    // InternalSqlParser.g:2419:6: ( (lv_pinany_4_0= rulePivotInClauseAny ) )
                    // InternalSqlParser.g:2420:1: (lv_pinany_4_0= rulePivotInClauseAny )
                    {
                    // InternalSqlParser.g:2420:1: (lv_pinany_4_0= rulePivotInClauseAny )
                    // InternalSqlParser.g:2421:3: lv_pinany_4_0= rulePivotInClauseAny
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getPivotInClauseAccess().getPinanyPivotInClauseAnyParserRuleCall_2_2_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_8);
                    lv_pinany_4_0=rulePivotInClauseAny();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getPivotInClauseRule());
                      	        }
                             		set(
                             			current, 
                             			"pinany",
                              		lv_pinany_4_0, 
                              		"com.jaspersoft.studio.data.Sql.PivotInClauseAny");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_5=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_5, grammarAccess.getPivotInClauseAccess().getRightParenthesisKeyword_3());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePivotInClause"


    // $ANTLR start "entryRulePivotInClauseAny"
    // InternalSqlParser.g:2450:1: entryRulePivotInClauseAny returns [String current=null] : iv_rulePivotInClauseAny= rulePivotInClauseAny EOF ;
    public final String entryRulePivotInClauseAny() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePivotInClauseAny = null;


        try {
            // InternalSqlParser.g:2451:1: (iv_rulePivotInClauseAny= rulePivotInClauseAny EOF )
            // InternalSqlParser.g:2452:2: iv_rulePivotInClauseAny= rulePivotInClauseAny EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPivotInClauseAnyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePivotInClauseAny=rulePivotInClauseAny();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePivotInClauseAny.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePivotInClauseAny"


    // $ANTLR start "rulePivotInClauseAny"
    // InternalSqlParser.g:2459:1: rulePivotInClauseAny returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= ANY (kw= Comma kw= ANY )* ) ;
    public final AntlrDatatypeRuleToken rulePivotInClauseAny() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // InternalSqlParser.g:2463:6: ( (kw= ANY (kw= Comma kw= ANY )* ) )
            // InternalSqlParser.g:2464:1: (kw= ANY (kw= Comma kw= ANY )* )
            {
            // InternalSqlParser.g:2464:1: (kw= ANY (kw= Comma kw= ANY )* )
            // InternalSqlParser.g:2465:2: kw= ANY (kw= Comma kw= ANY )*
            {
            kw=(Token)match(input,ANY,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current.merge(kw);
                      newLeafNode(kw, grammarAccess.getPivotInClauseAnyAccess().getANYKeyword_0()); 
                  
            }
            // InternalSqlParser.g:2470:1: (kw= Comma kw= ANY )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==Comma) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // InternalSqlParser.g:2471:2: kw= Comma kw= ANY
            	    {
            	    kw=(Token)match(input,Comma,FOLLOW_50); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	              current.merge(kw);
            	              newLeafNode(kw, grammarAccess.getPivotInClauseAnyAccess().getCommaKeyword_1_0()); 
            	          
            	    }
            	    kw=(Token)match(input,ANY,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	              current.merge(kw);
            	              newLeafNode(kw, grammarAccess.getPivotInClauseAnyAccess().getANYKeyword_1_1()); 
            	          
            	    }

            	    }
            	    break;

            	default :
            	    break loop51;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
                  
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePivotInClauseAny"


    // $ANTLR start "entryRuleUnpivotTable"
    // InternalSqlParser.g:2490:1: entryRuleUnpivotTable returns [EObject current=null] : iv_ruleUnpivotTable= ruleUnpivotTable EOF ;
    public final EObject entryRuleUnpivotTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnpivotTable = null;


        try {
            // InternalSqlParser.g:2491:2: (iv_ruleUnpivotTable= ruleUnpivotTable EOF )
            // InternalSqlParser.g:2492:2: iv_ruleUnpivotTable= ruleUnpivotTable EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnpivotTableRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUnpivotTable=ruleUnpivotTable();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUnpivotTable; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnpivotTable"


    // $ANTLR start "ruleUnpivotTable"
    // InternalSqlParser.g:2499:1: ruleUnpivotTable returns [EObject current=null] : (otherlv_0= UNPIVOT ( (otherlv_1= INCLUDE | otherlv_2= EXCLUDE ) otherlv_3= NULLS )? otherlv_4= LeftParenthesis ( (lv_pcols_5_0= rulePivotColumns ) ) ( (lv_pfor_6_0= rulePivotForClause ) ) ( (lv_inop_7_0= ruleUnpivotInClause ) ) otherlv_8= RightParenthesis ) ;
    public final EObject ruleUnpivotTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_8=null;
        EObject lv_pcols_5_0 = null;

        EObject lv_pfor_6_0 = null;

        EObject lv_inop_7_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:2502:28: ( (otherlv_0= UNPIVOT ( (otherlv_1= INCLUDE | otherlv_2= EXCLUDE ) otherlv_3= NULLS )? otherlv_4= LeftParenthesis ( (lv_pcols_5_0= rulePivotColumns ) ) ( (lv_pfor_6_0= rulePivotForClause ) ) ( (lv_inop_7_0= ruleUnpivotInClause ) ) otherlv_8= RightParenthesis ) )
            // InternalSqlParser.g:2503:1: (otherlv_0= UNPIVOT ( (otherlv_1= INCLUDE | otherlv_2= EXCLUDE ) otherlv_3= NULLS )? otherlv_4= LeftParenthesis ( (lv_pcols_5_0= rulePivotColumns ) ) ( (lv_pfor_6_0= rulePivotForClause ) ) ( (lv_inop_7_0= ruleUnpivotInClause ) ) otherlv_8= RightParenthesis )
            {
            // InternalSqlParser.g:2503:1: (otherlv_0= UNPIVOT ( (otherlv_1= INCLUDE | otherlv_2= EXCLUDE ) otherlv_3= NULLS )? otherlv_4= LeftParenthesis ( (lv_pcols_5_0= rulePivotColumns ) ) ( (lv_pfor_6_0= rulePivotForClause ) ) ( (lv_inop_7_0= ruleUnpivotInClause ) ) otherlv_8= RightParenthesis )
            // InternalSqlParser.g:2504:2: otherlv_0= UNPIVOT ( (otherlv_1= INCLUDE | otherlv_2= EXCLUDE ) otherlv_3= NULLS )? otherlv_4= LeftParenthesis ( (lv_pcols_5_0= rulePivotColumns ) ) ( (lv_pfor_6_0= rulePivotForClause ) ) ( (lv_inop_7_0= ruleUnpivotInClause ) ) otherlv_8= RightParenthesis
            {
            otherlv_0=(Token)match(input,UNPIVOT,FOLLOW_51); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getUnpivotTableAccess().getUNPIVOTKeyword_0());
                  
            }
            // InternalSqlParser.g:2508:1: ( (otherlv_1= INCLUDE | otherlv_2= EXCLUDE ) otherlv_3= NULLS )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==EXCLUDE||LA53_0==INCLUDE) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // InternalSqlParser.g:2508:2: (otherlv_1= INCLUDE | otherlv_2= EXCLUDE ) otherlv_3= NULLS
                    {
                    // InternalSqlParser.g:2508:2: (otherlv_1= INCLUDE | otherlv_2= EXCLUDE )
                    int alt52=2;
                    int LA52_0 = input.LA(1);

                    if ( (LA52_0==INCLUDE) ) {
                        alt52=1;
                    }
                    else if ( (LA52_0==EXCLUDE) ) {
                        alt52=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 52, 0, input);

                        throw nvae;
                    }
                    switch (alt52) {
                        case 1 :
                            // InternalSqlParser.g:2509:2: otherlv_1= INCLUDE
                            {
                            otherlv_1=(Token)match(input,INCLUDE,FOLLOW_52); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                  	newLeafNode(otherlv_1, grammarAccess.getUnpivotTableAccess().getINCLUDEKeyword_1_0_0());
                                  
                            }

                            }
                            break;
                        case 2 :
                            // InternalSqlParser.g:2515:2: otherlv_2= EXCLUDE
                            {
                            otherlv_2=(Token)match(input,EXCLUDE,FOLLOW_52); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                  	newLeafNode(otherlv_2, grammarAccess.getUnpivotTableAccess().getEXCLUDEKeyword_1_0_1());
                                  
                            }

                            }
                            break;

                    }

                    otherlv_3=(Token)match(input,NULLS,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_3, grammarAccess.getUnpivotTableAccess().getNULLSKeyword_1_1());
                          
                    }

                    }
                    break;

            }

            otherlv_4=(Token)match(input,LeftParenthesis,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_4, grammarAccess.getUnpivotTableAccess().getLeftParenthesisKeyword_2());
                  
            }
            // InternalSqlParser.g:2529:1: ( (lv_pcols_5_0= rulePivotColumns ) )
            // InternalSqlParser.g:2530:1: (lv_pcols_5_0= rulePivotColumns )
            {
            // InternalSqlParser.g:2530:1: (lv_pcols_5_0= rulePivotColumns )
            // InternalSqlParser.g:2531:3: lv_pcols_5_0= rulePivotColumns
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getUnpivotTableAccess().getPcolsPivotColumnsParserRuleCall_3_0()); 
              	    
            }
            pushFollow(FOLLOW_47);
            lv_pcols_5_0=rulePivotColumns();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getUnpivotTableRule());
              	        }
                     		set(
                     			current, 
                     			"pcols",
                      		lv_pcols_5_0, 
                      		"com.jaspersoft.studio.data.Sql.PivotColumns");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:2547:2: ( (lv_pfor_6_0= rulePivotForClause ) )
            // InternalSqlParser.g:2548:1: (lv_pfor_6_0= rulePivotForClause )
            {
            // InternalSqlParser.g:2548:1: (lv_pfor_6_0= rulePivotForClause )
            // InternalSqlParser.g:2549:3: lv_pfor_6_0= rulePivotForClause
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getUnpivotTableAccess().getPforPivotForClauseParserRuleCall_4_0()); 
              	    
            }
            pushFollow(FOLLOW_48);
            lv_pfor_6_0=rulePivotForClause();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getUnpivotTableRule());
              	        }
                     		set(
                     			current, 
                     			"pfor",
                      		lv_pfor_6_0, 
                      		"com.jaspersoft.studio.data.Sql.PivotForClause");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:2565:2: ( (lv_inop_7_0= ruleUnpivotInClause ) )
            // InternalSqlParser.g:2566:1: (lv_inop_7_0= ruleUnpivotInClause )
            {
            // InternalSqlParser.g:2566:1: (lv_inop_7_0= ruleUnpivotInClause )
            // InternalSqlParser.g:2567:3: lv_inop_7_0= ruleUnpivotInClause
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getUnpivotTableAccess().getInopUnpivotInClauseParserRuleCall_5_0()); 
              	    
            }
            pushFollow(FOLLOW_8);
            lv_inop_7_0=ruleUnpivotInClause();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getUnpivotTableRule());
              	        }
                     		set(
                     			current, 
                     			"inop",
                      		lv_inop_7_0, 
                      		"com.jaspersoft.studio.data.Sql.UnpivotInClause");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_8=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_8, grammarAccess.getUnpivotTableAccess().getRightParenthesisKeyword_6());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnpivotTable"


    // $ANTLR start "entryRuleUnpivotInClause"
    // InternalSqlParser.g:2596:1: entryRuleUnpivotInClause returns [EObject current=null] : iv_ruleUnpivotInClause= ruleUnpivotInClause EOF ;
    public final EObject entryRuleUnpivotInClause() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnpivotInClause = null;


        try {
            // InternalSqlParser.g:2597:2: (iv_ruleUnpivotInClause= ruleUnpivotInClause EOF )
            // InternalSqlParser.g:2598:2: iv_ruleUnpivotInClause= ruleUnpivotInClause EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnpivotInClauseRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUnpivotInClause=ruleUnpivotInClause();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUnpivotInClause; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnpivotInClause"


    // $ANTLR start "ruleUnpivotInClause"
    // InternalSqlParser.g:2605:1: ruleUnpivotInClause returns [EObject current=null] : ( () ( (lv_op_1_0= IN ) ) otherlv_2= LeftParenthesis ( (lv_args_3_0= ruleUnpivotInClauseArgs ) ) otherlv_4= RightParenthesis ) ;
    public final EObject ruleUnpivotInClause() throws RecognitionException {
        EObject current = null;

        Token lv_op_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_args_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:2608:28: ( ( () ( (lv_op_1_0= IN ) ) otherlv_2= LeftParenthesis ( (lv_args_3_0= ruleUnpivotInClauseArgs ) ) otherlv_4= RightParenthesis ) )
            // InternalSqlParser.g:2609:1: ( () ( (lv_op_1_0= IN ) ) otherlv_2= LeftParenthesis ( (lv_args_3_0= ruleUnpivotInClauseArgs ) ) otherlv_4= RightParenthesis )
            {
            // InternalSqlParser.g:2609:1: ( () ( (lv_op_1_0= IN ) ) otherlv_2= LeftParenthesis ( (lv_args_3_0= ruleUnpivotInClauseArgs ) ) otherlv_4= RightParenthesis )
            // InternalSqlParser.g:2609:2: () ( (lv_op_1_0= IN ) ) otherlv_2= LeftParenthesis ( (lv_args_3_0= ruleUnpivotInClauseArgs ) ) otherlv_4= RightParenthesis
            {
            // InternalSqlParser.g:2609:2: ()
            // InternalSqlParser.g:2610:2: 
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getUnpivotInClauseAccess().getUnipivotInClauseAction_0(),
                          current);
                  
            }

            }

            // InternalSqlParser.g:2618:2: ( (lv_op_1_0= IN ) )
            // InternalSqlParser.g:2619:1: (lv_op_1_0= IN )
            {
            // InternalSqlParser.g:2619:1: (lv_op_1_0= IN )
            // InternalSqlParser.g:2620:3: lv_op_1_0= IN
            {
            lv_op_1_0=(Token)match(input,IN,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      newLeafNode(lv_op_1_0, grammarAccess.getUnpivotInClauseAccess().getOpINKeyword_1_0());
                  
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getUnpivotInClauseRule());
              	        }
                     		setWithLastConsumed(current, "op", lv_op_1_0, "IN");
              	    
            }

            }


            }

            otherlv_2=(Token)match(input,LeftParenthesis,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getUnpivotInClauseAccess().getLeftParenthesisKeyword_2());
                  
            }
            // InternalSqlParser.g:2639:1: ( (lv_args_3_0= ruleUnpivotInClauseArgs ) )
            // InternalSqlParser.g:2640:1: (lv_args_3_0= ruleUnpivotInClauseArgs )
            {
            // InternalSqlParser.g:2640:1: (lv_args_3_0= ruleUnpivotInClauseArgs )
            // InternalSqlParser.g:2641:3: lv_args_3_0= ruleUnpivotInClauseArgs
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getUnpivotInClauseAccess().getArgsUnpivotInClauseArgsParserRuleCall_3_0()); 
              	    
            }
            pushFollow(FOLLOW_8);
            lv_args_3_0=ruleUnpivotInClauseArgs();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getUnpivotInClauseRule());
              	        }
                     		set(
                     			current, 
                     			"args",
                      		lv_args_3_0, 
                      		"com.jaspersoft.studio.data.Sql.UnpivotInClauseArgs");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_4=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_4, grammarAccess.getUnpivotInClauseAccess().getRightParenthesisKeyword_4());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnpivotInClause"


    // $ANTLR start "entryRuleUnpivotInClauseArgs"
    // InternalSqlParser.g:2670:1: entryRuleUnpivotInClauseArgs returns [EObject current=null] : iv_ruleUnpivotInClauseArgs= ruleUnpivotInClauseArgs EOF ;
    public final EObject entryRuleUnpivotInClauseArgs() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnpivotInClauseArgs = null;


        try {
            // InternalSqlParser.g:2671:2: (iv_ruleUnpivotInClauseArgs= ruleUnpivotInClauseArgs EOF )
            // InternalSqlParser.g:2672:2: iv_ruleUnpivotInClauseArgs= ruleUnpivotInClauseArgs EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnpivotInClauseArgsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUnpivotInClauseArgs=ruleUnpivotInClauseArgs();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUnpivotInClauseArgs; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnpivotInClauseArgs"


    // $ANTLR start "ruleUnpivotInClauseArgs"
    // InternalSqlParser.g:2679:1: ruleUnpivotInClauseArgs returns [EObject current=null] : (this_UnpivotInClauseArg_0= ruleUnpivotInClauseArg ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleUnpivotInClauseArg ) ) )+ )? ) ;
    public final EObject ruleUnpivotInClauseArgs() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_UnpivotInClauseArg_0 = null;

        EObject lv_entries_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:2682:28: ( (this_UnpivotInClauseArg_0= ruleUnpivotInClauseArg ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleUnpivotInClauseArg ) ) )+ )? ) )
            // InternalSqlParser.g:2683:1: (this_UnpivotInClauseArg_0= ruleUnpivotInClauseArg ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleUnpivotInClauseArg ) ) )+ )? )
            {
            // InternalSqlParser.g:2683:1: (this_UnpivotInClauseArg_0= ruleUnpivotInClauseArg ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleUnpivotInClauseArg ) ) )+ )? )
            // InternalSqlParser.g:2684:2: this_UnpivotInClauseArg_0= ruleUnpivotInClauseArg ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleUnpivotInClauseArg ) ) )+ )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getUnpivotInClauseArgsAccess().getUnpivotInClauseArgParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_9);
            this_UnpivotInClauseArg_0=ruleUnpivotInClauseArg();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_UnpivotInClauseArg_0;
                      afterParserOrEnumRuleCall();
                  
            }
            // InternalSqlParser.g:2695:1: ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleUnpivotInClauseArg ) ) )+ )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==Comma) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // InternalSqlParser.g:2695:2: () (otherlv_2= Comma ( (lv_entries_3_0= ruleUnpivotInClauseArg ) ) )+
                    {
                    // InternalSqlParser.g:2695:2: ()
                    // InternalSqlParser.g:2696:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getUnpivotInClauseArgsAccess().getUicargsEntriesAction_1_0(),
                                  current);
                          
                    }

                    }

                    // InternalSqlParser.g:2704:2: (otherlv_2= Comma ( (lv_entries_3_0= ruleUnpivotInClauseArg ) ) )+
                    int cnt54=0;
                    loop54:
                    do {
                        int alt54=2;
                        int LA54_0 = input.LA(1);

                        if ( (LA54_0==Comma) ) {
                            alt54=1;
                        }


                        switch (alt54) {
                    	case 1 :
                    	    // InternalSqlParser.g:2705:2: otherlv_2= Comma ( (lv_entries_3_0= ruleUnpivotInClauseArg ) )
                    	    {
                    	    otherlv_2=(Token)match(input,Comma,FOLLOW_21); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_2, grammarAccess.getUnpivotInClauseArgsAccess().getCommaKeyword_1_1_0());
                    	          
                    	    }
                    	    // InternalSqlParser.g:2709:1: ( (lv_entries_3_0= ruleUnpivotInClauseArg ) )
                    	    // InternalSqlParser.g:2710:1: (lv_entries_3_0= ruleUnpivotInClauseArg )
                    	    {
                    	    // InternalSqlParser.g:2710:1: (lv_entries_3_0= ruleUnpivotInClauseArg )
                    	    // InternalSqlParser.g:2711:3: lv_entries_3_0= ruleUnpivotInClauseArg
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getUnpivotInClauseArgsAccess().getEntriesUnpivotInClauseArgParserRuleCall_1_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_9);
                    	    lv_entries_3_0=ruleUnpivotInClauseArg();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getUnpivotInClauseArgsRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"entries",
                    	              		lv_entries_3_0, 
                    	              		"com.jaspersoft.studio.data.Sql.UnpivotInClauseArg");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt54 >= 1 ) break loop54;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(54, input);
                                throw eee;
                        }
                        cnt54++;
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnpivotInClauseArgs"


    // $ANTLR start "entryRuleUnpivotInClauseArg"
    // InternalSqlParser.g:2735:1: entryRuleUnpivotInClauseArg returns [EObject current=null] : iv_ruleUnpivotInClauseArg= ruleUnpivotInClauseArg EOF ;
    public final EObject entryRuleUnpivotInClauseArg() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnpivotInClauseArg = null;


        try {
            // InternalSqlParser.g:2736:2: (iv_ruleUnpivotInClauseArg= ruleUnpivotInClauseArg EOF )
            // InternalSqlParser.g:2737:2: iv_ruleUnpivotInClauseArg= ruleUnpivotInClauseArg EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnpivotInClauseArgRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUnpivotInClauseArg=ruleUnpivotInClauseArg();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUnpivotInClauseArg; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnpivotInClauseArg"


    // $ANTLR start "ruleUnpivotInClauseArg"
    // InternalSqlParser.g:2744:1: ruleUnpivotInClauseArg returns [EObject current=null] : ( ( (lv_pcols_0_0= rulePivotColumns ) ) (otherlv_1= AS ( (lv_cfuls_2_0= rulePivotColumns ) ) )? ) ;
    public final EObject ruleUnpivotInClauseArg() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_pcols_0_0 = null;

        EObject lv_cfuls_2_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:2747:28: ( ( ( (lv_pcols_0_0= rulePivotColumns ) ) (otherlv_1= AS ( (lv_cfuls_2_0= rulePivotColumns ) ) )? ) )
            // InternalSqlParser.g:2748:1: ( ( (lv_pcols_0_0= rulePivotColumns ) ) (otherlv_1= AS ( (lv_cfuls_2_0= rulePivotColumns ) ) )? )
            {
            // InternalSqlParser.g:2748:1: ( ( (lv_pcols_0_0= rulePivotColumns ) ) (otherlv_1= AS ( (lv_cfuls_2_0= rulePivotColumns ) ) )? )
            // InternalSqlParser.g:2748:2: ( (lv_pcols_0_0= rulePivotColumns ) ) (otherlv_1= AS ( (lv_cfuls_2_0= rulePivotColumns ) ) )?
            {
            // InternalSqlParser.g:2748:2: ( (lv_pcols_0_0= rulePivotColumns ) )
            // InternalSqlParser.g:2749:1: (lv_pcols_0_0= rulePivotColumns )
            {
            // InternalSqlParser.g:2749:1: (lv_pcols_0_0= rulePivotColumns )
            // InternalSqlParser.g:2750:3: lv_pcols_0_0= rulePivotColumns
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getUnpivotInClauseArgAccess().getPcolsPivotColumnsParserRuleCall_0_0()); 
              	    
            }
            pushFollow(FOLLOW_53);
            lv_pcols_0_0=rulePivotColumns();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getUnpivotInClauseArgRule());
              	        }
                     		set(
                     			current, 
                     			"pcols",
                      		lv_pcols_0_0, 
                      		"com.jaspersoft.studio.data.Sql.PivotColumns");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:2766:2: (otherlv_1= AS ( (lv_cfuls_2_0= rulePivotColumns ) ) )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==AS) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // InternalSqlParser.g:2767:2: otherlv_1= AS ( (lv_cfuls_2_0= rulePivotColumns ) )
                    {
                    otherlv_1=(Token)match(input,AS,FOLLOW_21); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_1, grammarAccess.getUnpivotInClauseArgAccess().getASKeyword_1_0());
                          
                    }
                    // InternalSqlParser.g:2771:1: ( (lv_cfuls_2_0= rulePivotColumns ) )
                    // InternalSqlParser.g:2772:1: (lv_cfuls_2_0= rulePivotColumns )
                    {
                    // InternalSqlParser.g:2772:1: (lv_cfuls_2_0= rulePivotColumns )
                    // InternalSqlParser.g:2773:3: lv_cfuls_2_0= rulePivotColumns
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getUnpivotInClauseArgAccess().getCfulsPivotColumnsParserRuleCall_1_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_cfuls_2_0=rulePivotColumns();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getUnpivotInClauseArgRule());
                      	        }
                             		set(
                             			current, 
                             			"cfuls",
                              		lv_cfuls_2_0, 
                              		"com.jaspersoft.studio.data.Sql.PivotColumns");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnpivotInClauseArg"


    // $ANTLR start "entryRulePivotForClause"
    // InternalSqlParser.g:2797:1: entryRulePivotForClause returns [EObject current=null] : iv_rulePivotForClause= rulePivotForClause EOF ;
    public final EObject entryRulePivotForClause() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePivotForClause = null;


        try {
            // InternalSqlParser.g:2798:2: (iv_rulePivotForClause= rulePivotForClause EOF )
            // InternalSqlParser.g:2799:2: iv_rulePivotForClause= rulePivotForClause EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPivotForClauseRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePivotForClause=rulePivotForClause();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePivotForClause; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePivotForClause"


    // $ANTLR start "rulePivotForClause"
    // InternalSqlParser.g:2806:1: rulePivotForClause returns [EObject current=null] : (otherlv_0= FOR (this_ColumnFull_1= ruleColumnFull | (otherlv_2= LeftParenthesis this_Columns_3= ruleColumns otherlv_4= RightParenthesis ) ) ) ;
    public final EObject rulePivotForClause() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject this_ColumnFull_1 = null;

        EObject this_Columns_3 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:2809:28: ( (otherlv_0= FOR (this_ColumnFull_1= ruleColumnFull | (otherlv_2= LeftParenthesis this_Columns_3= ruleColumns otherlv_4= RightParenthesis ) ) ) )
            // InternalSqlParser.g:2810:1: (otherlv_0= FOR (this_ColumnFull_1= ruleColumnFull | (otherlv_2= LeftParenthesis this_Columns_3= ruleColumns otherlv_4= RightParenthesis ) ) )
            {
            // InternalSqlParser.g:2810:1: (otherlv_0= FOR (this_ColumnFull_1= ruleColumnFull | (otherlv_2= LeftParenthesis this_Columns_3= ruleColumns otherlv_4= RightParenthesis ) ) )
            // InternalSqlParser.g:2811:2: otherlv_0= FOR (this_ColumnFull_1= ruleColumnFull | (otherlv_2= LeftParenthesis this_Columns_3= ruleColumns otherlv_4= RightParenthesis ) )
            {
            otherlv_0=(Token)match(input,FOR,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getPivotForClauseAccess().getFORKeyword_0());
                  
            }
            // InternalSqlParser.g:2815:1: (this_ColumnFull_1= ruleColumnFull | (otherlv_2= LeftParenthesis this_Columns_3= ruleColumns otherlv_4= RightParenthesis ) )
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( ((LA57_0>=RULE_STRING && LA57_0<=RULE_ID)) ) {
                alt57=1;
            }
            else if ( (LA57_0==LeftParenthesis) ) {
                alt57=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 57, 0, input);

                throw nvae;
            }
            switch (alt57) {
                case 1 :
                    // InternalSqlParser.g:2816:2: this_ColumnFull_1= ruleColumnFull
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getPivotForClauseAccess().getColumnFullParserRuleCall_1_0()); 
                          
                    }
                    pushFollow(FOLLOW_2);
                    this_ColumnFull_1=ruleColumnFull();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_ColumnFull_1;
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:2828:6: (otherlv_2= LeftParenthesis this_Columns_3= ruleColumns otherlv_4= RightParenthesis )
                    {
                    // InternalSqlParser.g:2828:6: (otherlv_2= LeftParenthesis this_Columns_3= ruleColumns otherlv_4= RightParenthesis )
                    // InternalSqlParser.g:2829:2: otherlv_2= LeftParenthesis this_Columns_3= ruleColumns otherlv_4= RightParenthesis
                    {
                    otherlv_2=(Token)match(input,LeftParenthesis,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_2, grammarAccess.getPivotForClauseAccess().getLeftParenthesisKeyword_1_1_0());
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getPivotForClauseAccess().getColumnsParserRuleCall_1_1_1()); 
                          
                    }
                    pushFollow(FOLLOW_8);
                    this_Columns_3=ruleColumns();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_Columns_3;
                              afterParserOrEnumRuleCall();
                          
                    }
                    otherlv_4=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_4, grammarAccess.getPivotForClauseAccess().getRightParenthesisKeyword_1_1_2());
                          
                    }

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePivotForClause"


    // $ANTLR start "entryRulePivotColumns"
    // InternalSqlParser.g:2858:1: entryRulePivotColumns returns [EObject current=null] : iv_rulePivotColumns= rulePivotColumns EOF ;
    public final EObject entryRulePivotColumns() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePivotColumns = null;


        try {
            // InternalSqlParser.g:2859:2: (iv_rulePivotColumns= rulePivotColumns EOF )
            // InternalSqlParser.g:2860:2: iv_rulePivotColumns= rulePivotColumns EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPivotColumnsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePivotColumns=rulePivotColumns();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePivotColumns; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePivotColumns"


    // $ANTLR start "rulePivotColumns"
    // InternalSqlParser.g:2867:1: rulePivotColumns returns [EObject current=null] : (this_PivotCol_0= rulePivotCol | (otherlv_1= LeftParenthesis this_PivotCols_2= rulePivotCols otherlv_3= RightParenthesis ) ) ;
    public final EObject rulePivotColumns() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject this_PivotCol_0 = null;

        EObject this_PivotCols_2 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:2870:28: ( (this_PivotCol_0= rulePivotCol | (otherlv_1= LeftParenthesis this_PivotCols_2= rulePivotCols otherlv_3= RightParenthesis ) ) )
            // InternalSqlParser.g:2871:1: (this_PivotCol_0= rulePivotCol | (otherlv_1= LeftParenthesis this_PivotCols_2= rulePivotCols otherlv_3= RightParenthesis ) )
            {
            // InternalSqlParser.g:2871:1: (this_PivotCol_0= rulePivotCol | (otherlv_1= LeftParenthesis this_PivotCols_2= rulePivotCols otherlv_3= RightParenthesis ) )
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( ((LA58_0>=RULE_STRING && LA58_0<=RULE_ID)) ) {
                alt58=1;
            }
            else if ( (LA58_0==LeftParenthesis) ) {
                alt58=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 58, 0, input);

                throw nvae;
            }
            switch (alt58) {
                case 1 :
                    // InternalSqlParser.g:2872:2: this_PivotCol_0= rulePivotCol
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getPivotColumnsAccess().getPivotColParserRuleCall_0()); 
                          
                    }
                    pushFollow(FOLLOW_2);
                    this_PivotCol_0=rulePivotCol();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_PivotCol_0;
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:2884:6: (otherlv_1= LeftParenthesis this_PivotCols_2= rulePivotCols otherlv_3= RightParenthesis )
                    {
                    // InternalSqlParser.g:2884:6: (otherlv_1= LeftParenthesis this_PivotCols_2= rulePivotCols otherlv_3= RightParenthesis )
                    // InternalSqlParser.g:2885:2: otherlv_1= LeftParenthesis this_PivotCols_2= rulePivotCols otherlv_3= RightParenthesis
                    {
                    otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_1, grammarAccess.getPivotColumnsAccess().getLeftParenthesisKeyword_1_0());
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getPivotColumnsAccess().getPivotColsParserRuleCall_1_1()); 
                          
                    }
                    pushFollow(FOLLOW_8);
                    this_PivotCols_2=rulePivotCols();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_PivotCols_2;
                              afterParserOrEnumRuleCall();
                          
                    }
                    otherlv_3=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_3, grammarAccess.getPivotColumnsAccess().getRightParenthesisKeyword_1_2());
                          
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePivotColumns"


    // $ANTLR start "entryRulePivotCols"
    // InternalSqlParser.g:2914:1: entryRulePivotCols returns [EObject current=null] : iv_rulePivotCols= rulePivotCols EOF ;
    public final EObject entryRulePivotCols() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePivotCols = null;


        try {
            // InternalSqlParser.g:2915:2: (iv_rulePivotCols= rulePivotCols EOF )
            // InternalSqlParser.g:2916:2: iv_rulePivotCols= rulePivotCols EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPivotColsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePivotCols=rulePivotCols();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePivotCols; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePivotCols"


    // $ANTLR start "rulePivotCols"
    // InternalSqlParser.g:2923:1: rulePivotCols returns [EObject current=null] : (this_PivotCol_0= rulePivotCol ( () (otherlv_2= Comma ( (lv_entries_3_0= rulePivotCol ) ) )+ )? ) ;
    public final EObject rulePivotCols() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_PivotCol_0 = null;

        EObject lv_entries_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:2926:28: ( (this_PivotCol_0= rulePivotCol ( () (otherlv_2= Comma ( (lv_entries_3_0= rulePivotCol ) ) )+ )? ) )
            // InternalSqlParser.g:2927:1: (this_PivotCol_0= rulePivotCol ( () (otherlv_2= Comma ( (lv_entries_3_0= rulePivotCol ) ) )+ )? )
            {
            // InternalSqlParser.g:2927:1: (this_PivotCol_0= rulePivotCol ( () (otherlv_2= Comma ( (lv_entries_3_0= rulePivotCol ) ) )+ )? )
            // InternalSqlParser.g:2928:2: this_PivotCol_0= rulePivotCol ( () (otherlv_2= Comma ( (lv_entries_3_0= rulePivotCol ) ) )+ )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getPivotColsAccess().getPivotColParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_9);
            this_PivotCol_0=rulePivotCol();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_PivotCol_0;
                      afterParserOrEnumRuleCall();
                  
            }
            // InternalSqlParser.g:2939:1: ( () (otherlv_2= Comma ( (lv_entries_3_0= rulePivotCol ) ) )+ )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==Comma) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // InternalSqlParser.g:2939:2: () (otherlv_2= Comma ( (lv_entries_3_0= rulePivotCol ) ) )+
                    {
                    // InternalSqlParser.g:2939:2: ()
                    // InternalSqlParser.g:2940:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getPivotColsAccess().getPvcsEntriesAction_1_0(),
                                  current);
                          
                    }

                    }

                    // InternalSqlParser.g:2948:2: (otherlv_2= Comma ( (lv_entries_3_0= rulePivotCol ) ) )+
                    int cnt59=0;
                    loop59:
                    do {
                        int alt59=2;
                        int LA59_0 = input.LA(1);

                        if ( (LA59_0==Comma) ) {
                            alt59=1;
                        }


                        switch (alt59) {
                    	case 1 :
                    	    // InternalSqlParser.g:2949:2: otherlv_2= Comma ( (lv_entries_3_0= rulePivotCol ) )
                    	    {
                    	    otherlv_2=(Token)match(input,Comma,FOLLOW_4); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_2, grammarAccess.getPivotColsAccess().getCommaKeyword_1_1_0());
                    	          
                    	    }
                    	    // InternalSqlParser.g:2953:1: ( (lv_entries_3_0= rulePivotCol ) )
                    	    // InternalSqlParser.g:2954:1: (lv_entries_3_0= rulePivotCol )
                    	    {
                    	    // InternalSqlParser.g:2954:1: (lv_entries_3_0= rulePivotCol )
                    	    // InternalSqlParser.g:2955:3: lv_entries_3_0= rulePivotCol
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getPivotColsAccess().getEntriesPivotColParserRuleCall_1_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_9);
                    	    lv_entries_3_0=rulePivotCol();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getPivotColsRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"entries",
                    	              		lv_entries_3_0, 
                    	              		"com.jaspersoft.studio.data.Sql.PivotCol");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt59 >= 1 ) break loop59;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(59, input);
                                throw eee;
                        }
                        cnt59++;
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePivotCols"


    // $ANTLR start "entryRulePivotCol"
    // InternalSqlParser.g:2979:1: entryRulePivotCol returns [EObject current=null] : iv_rulePivotCol= rulePivotCol EOF ;
    public final EObject entryRulePivotCol() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePivotCol = null;


        try {
            // InternalSqlParser.g:2980:2: (iv_rulePivotCol= rulePivotCol EOF )
            // InternalSqlParser.g:2981:2: iv_rulePivotCol= rulePivotCol EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPivotColRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePivotCol=rulePivotCol();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePivotCol; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePivotCol"


    // $ANTLR start "rulePivotCol"
    // InternalSqlParser.g:2988:1: rulePivotCol returns [EObject current=null] : (this_DbObjectName_0= ruleDbObjectName ( () (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )? ) ;
    public final EObject rulePivotCol() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_DbObjectName_0 = null;

        EObject lv_entries_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:2991:28: ( (this_DbObjectName_0= ruleDbObjectName ( () (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )? ) )
            // InternalSqlParser.g:2992:1: (this_DbObjectName_0= ruleDbObjectName ( () (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )? )
            {
            // InternalSqlParser.g:2992:1: (this_DbObjectName_0= ruleDbObjectName ( () (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )? )
            // InternalSqlParser.g:2993:2: this_DbObjectName_0= ruleDbObjectName ( () (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getPivotColAccess().getDbObjectNameParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_37);
            this_DbObjectName_0=ruleDbObjectName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_DbObjectName_0;
                      afterParserOrEnumRuleCall();
                  
            }
            // InternalSqlParser.g:3004:1: ( () (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==FullStop) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // InternalSqlParser.g:3004:2: () (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+
                    {
                    // InternalSqlParser.g:3004:2: ()
                    // InternalSqlParser.g:3005:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getPivotColAccess().getPcolsEntriesAction_1_0(),
                                  current);
                          
                    }

                    }

                    // InternalSqlParser.g:3013:2: (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+
                    int cnt61=0;
                    loop61:
                    do {
                        int alt61=2;
                        int LA61_0 = input.LA(1);

                        if ( (LA61_0==FullStop) ) {
                            alt61=1;
                        }


                        switch (alt61) {
                    	case 1 :
                    	    // InternalSqlParser.g:3014:2: otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) )
                    	    {
                    	    otherlv_2=(Token)match(input,FullStop,FOLLOW_4); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_2, grammarAccess.getPivotColAccess().getFullStopKeyword_1_1_0());
                    	          
                    	    }
                    	    // InternalSqlParser.g:3018:1: ( (lv_entries_3_0= ruleDbObjectName ) )
                    	    // InternalSqlParser.g:3019:1: (lv_entries_3_0= ruleDbObjectName )
                    	    {
                    	    // InternalSqlParser.g:3019:1: (lv_entries_3_0= ruleDbObjectName )
                    	    // InternalSqlParser.g:3020:3: lv_entries_3_0= ruleDbObjectName
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getPivotColAccess().getEntriesDbObjectNameParserRuleCall_1_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_37);
                    	    lv_entries_3_0=ruleDbObjectName();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getPivotColRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"entries",
                    	              		lv_entries_3_0, 
                    	              		"com.jaspersoft.studio.data.Sql.DbObjectName");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt61 >= 1 ) break loop61;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(61, input);
                                throw eee;
                        }
                        cnt61++;
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePivotCol"


    // $ANTLR start "entryRuleTableFull"
    // InternalSqlParser.g:3044:1: entryRuleTableFull returns [EObject current=null] : iv_ruleTableFull= ruleTableFull EOF ;
    public final EObject entryRuleTableFull() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableFull = null;


        try {
            // InternalSqlParser.g:3045:2: (iv_ruleTableFull= ruleTableFull EOF )
            // InternalSqlParser.g:3046:2: iv_ruleTableFull= ruleTableFull EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTableFullRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTableFull=ruleTableFull();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTableFull; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTableFull"


    // $ANTLR start "ruleTableFull"
    // InternalSqlParser.g:3053:1: ruleTableFull returns [EObject current=null] : (this_DbObjectName_0= ruleDbObjectName ( () (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )? ) ;
    public final EObject ruleTableFull() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_DbObjectName_0 = null;

        EObject lv_entries_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:3056:28: ( (this_DbObjectName_0= ruleDbObjectName ( () (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )? ) )
            // InternalSqlParser.g:3057:1: (this_DbObjectName_0= ruleDbObjectName ( () (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )? )
            {
            // InternalSqlParser.g:3057:1: (this_DbObjectName_0= ruleDbObjectName ( () (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )? )
            // InternalSqlParser.g:3058:2: this_DbObjectName_0= ruleDbObjectName ( () (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getTableFullAccess().getDbObjectNameParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_37);
            this_DbObjectName_0=ruleDbObjectName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_DbObjectName_0;
                      afterParserOrEnumRuleCall();
                  
            }
            // InternalSqlParser.g:3069:1: ( () (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+ )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==FullStop) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // InternalSqlParser.g:3069:2: () (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+
                    {
                    // InternalSqlParser.g:3069:2: ()
                    // InternalSqlParser.g:3070:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getTableFullAccess().getTblsEntriesAction_1_0(),
                                  current);
                          
                    }

                    }

                    // InternalSqlParser.g:3078:2: (otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) ) )+
                    int cnt63=0;
                    loop63:
                    do {
                        int alt63=2;
                        int LA63_0 = input.LA(1);

                        if ( (LA63_0==FullStop) ) {
                            alt63=1;
                        }


                        switch (alt63) {
                    	case 1 :
                    	    // InternalSqlParser.g:3079:2: otherlv_2= FullStop ( (lv_entries_3_0= ruleDbObjectName ) )
                    	    {
                    	    otherlv_2=(Token)match(input,FullStop,FOLLOW_4); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_2, grammarAccess.getTableFullAccess().getFullStopKeyword_1_1_0());
                    	          
                    	    }
                    	    // InternalSqlParser.g:3083:1: ( (lv_entries_3_0= ruleDbObjectName ) )
                    	    // InternalSqlParser.g:3084:1: (lv_entries_3_0= ruleDbObjectName )
                    	    {
                    	    // InternalSqlParser.g:3084:1: (lv_entries_3_0= ruleDbObjectName )
                    	    // InternalSqlParser.g:3085:3: lv_entries_3_0= ruleDbObjectName
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getTableFullAccess().getEntriesDbObjectNameParserRuleCall_1_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_37);
                    	    lv_entries_3_0=ruleDbObjectName();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getTableFullRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"entries",
                    	              		lv_entries_3_0, 
                    	              		"com.jaspersoft.studio.data.Sql.DbObjectName");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt63 >= 1 ) break loop63;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(63, input);
                                throw eee;
                        }
                        cnt63++;
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTableFull"


    // $ANTLR start "entryRuleDbObjectNameAll"
    // InternalSqlParser.g:3109:1: entryRuleDbObjectNameAll returns [EObject current=null] : iv_ruleDbObjectNameAll= ruleDbObjectNameAll EOF ;
    public final EObject entryRuleDbObjectNameAll() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDbObjectNameAll = null;


        try {
            // InternalSqlParser.g:3110:2: (iv_ruleDbObjectNameAll= ruleDbObjectNameAll EOF )
            // InternalSqlParser.g:3111:2: iv_ruleDbObjectNameAll= ruleDbObjectNameAll EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDbObjectNameAllRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDbObjectNameAll=ruleDbObjectNameAll();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDbObjectNameAll; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDbObjectNameAll"


    // $ANTLR start "ruleDbObjectNameAll"
    // InternalSqlParser.g:3118:1: ruleDbObjectNameAll returns [EObject current=null] : ( ( (lv_dbname_0_0= ruleDBID ) ) otherlv_1= FullStop this_STAR_2= RULE_STAR ) ;
    public final EObject ruleDbObjectNameAll() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token this_STAR_2=null;
        AntlrDatatypeRuleToken lv_dbname_0_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:3121:28: ( ( ( (lv_dbname_0_0= ruleDBID ) ) otherlv_1= FullStop this_STAR_2= RULE_STAR ) )
            // InternalSqlParser.g:3122:1: ( ( (lv_dbname_0_0= ruleDBID ) ) otherlv_1= FullStop this_STAR_2= RULE_STAR )
            {
            // InternalSqlParser.g:3122:1: ( ( (lv_dbname_0_0= ruleDBID ) ) otherlv_1= FullStop this_STAR_2= RULE_STAR )
            // InternalSqlParser.g:3122:2: ( (lv_dbname_0_0= ruleDBID ) ) otherlv_1= FullStop this_STAR_2= RULE_STAR
            {
            // InternalSqlParser.g:3122:2: ( (lv_dbname_0_0= ruleDBID ) )
            // InternalSqlParser.g:3123:1: (lv_dbname_0_0= ruleDBID )
            {
            // InternalSqlParser.g:3123:1: (lv_dbname_0_0= ruleDBID )
            // InternalSqlParser.g:3124:3: lv_dbname_0_0= ruleDBID
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getDbObjectNameAllAccess().getDbnameDBIDParserRuleCall_0_0()); 
              	    
            }
            pushFollow(FOLLOW_54);
            lv_dbname_0_0=ruleDBID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getDbObjectNameAllRule());
              	        }
                     		set(
                     			current, 
                     			"dbname",
                      		lv_dbname_0_0, 
                      		"com.jaspersoft.studio.data.Sql.DBID");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_1=(Token)match(input,FullStop,FOLLOW_55); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getDbObjectNameAllAccess().getFullStopKeyword_1());
                  
            }
            this_STAR_2=(Token)match(input,RULE_STAR,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                  newLeafNode(this_STAR_2, grammarAccess.getDbObjectNameAllAccess().getSTARTerminalRuleCall_2()); 
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDbObjectNameAll"


    // $ANTLR start "entryRuleDbObjectName"
    // InternalSqlParser.g:3157:1: entryRuleDbObjectName returns [EObject current=null] : iv_ruleDbObjectName= ruleDbObjectName EOF ;
    public final EObject entryRuleDbObjectName() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDbObjectName = null;


        try {
            // InternalSqlParser.g:3158:2: (iv_ruleDbObjectName= ruleDbObjectName EOF )
            // InternalSqlParser.g:3159:2: iv_ruleDbObjectName= ruleDbObjectName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDbObjectNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDbObjectName=ruleDbObjectName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDbObjectName; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDbObjectName"


    // $ANTLR start "ruleDbObjectName"
    // InternalSqlParser.g:3166:1: ruleDbObjectName returns [EObject current=null] : ( (lv_dbname_0_0= ruleDBID ) ) ;
    public final EObject ruleDbObjectName() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_dbname_0_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:3169:28: ( ( (lv_dbname_0_0= ruleDBID ) ) )
            // InternalSqlParser.g:3170:1: ( (lv_dbname_0_0= ruleDBID ) )
            {
            // InternalSqlParser.g:3170:1: ( (lv_dbname_0_0= ruleDBID ) )
            // InternalSqlParser.g:3171:1: (lv_dbname_0_0= ruleDBID )
            {
            // InternalSqlParser.g:3171:1: (lv_dbname_0_0= ruleDBID )
            // InternalSqlParser.g:3172:3: lv_dbname_0_0= ruleDBID
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getDbObjectNameAccess().getDbnameDBIDParserRuleCall_0()); 
              	    
            }
            pushFollow(FOLLOW_2);
            lv_dbname_0_0=ruleDBID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getDbObjectNameRule());
              	        }
                     		set(
                     			current, 
                     			"dbname",
                      		lv_dbname_0_0, 
                      		"com.jaspersoft.studio.data.Sql.DBID");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDbObjectName"


    // $ANTLR start "entryRuleOrderByColumns"
    // InternalSqlParser.g:3196:1: entryRuleOrderByColumns returns [EObject current=null] : iv_ruleOrderByColumns= ruleOrderByColumns EOF ;
    public final EObject entryRuleOrderByColumns() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrderByColumns = null;


        try {
            // InternalSqlParser.g:3197:2: (iv_ruleOrderByColumns= ruleOrderByColumns EOF )
            // InternalSqlParser.g:3198:2: iv_ruleOrderByColumns= ruleOrderByColumns EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOrderByColumnsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOrderByColumns=ruleOrderByColumns();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOrderByColumns; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOrderByColumns"


    // $ANTLR start "ruleOrderByColumns"
    // InternalSqlParser.g:3205:1: ruleOrderByColumns returns [EObject current=null] : (this_OrderByColumnFull_0= ruleOrderByColumnFull ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleOrderByColumnFull ) ) )+ )? ) ;
    public final EObject ruleOrderByColumns() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_OrderByColumnFull_0 = null;

        EObject lv_entries_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:3208:28: ( (this_OrderByColumnFull_0= ruleOrderByColumnFull ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleOrderByColumnFull ) ) )+ )? ) )
            // InternalSqlParser.g:3209:1: (this_OrderByColumnFull_0= ruleOrderByColumnFull ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleOrderByColumnFull ) ) )+ )? )
            {
            // InternalSqlParser.g:3209:1: (this_OrderByColumnFull_0= ruleOrderByColumnFull ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleOrderByColumnFull ) ) )+ )? )
            // InternalSqlParser.g:3210:2: this_OrderByColumnFull_0= ruleOrderByColumnFull ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleOrderByColumnFull ) ) )+ )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getOrderByColumnsAccess().getOrderByColumnFullParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_9);
            this_OrderByColumnFull_0=ruleOrderByColumnFull();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_OrderByColumnFull_0;
                      afterParserOrEnumRuleCall();
                  
            }
            // InternalSqlParser.g:3221:1: ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleOrderByColumnFull ) ) )+ )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==Comma) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // InternalSqlParser.g:3221:2: () (otherlv_2= Comma ( (lv_entries_3_0= ruleOrderByColumnFull ) ) )+
                    {
                    // InternalSqlParser.g:3221:2: ()
                    // InternalSqlParser.g:3222:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getOrderByColumnsAccess().getOrOrderByColumnEntriesAction_1_0(),
                                  current);
                          
                    }

                    }

                    // InternalSqlParser.g:3230:2: (otherlv_2= Comma ( (lv_entries_3_0= ruleOrderByColumnFull ) ) )+
                    int cnt65=0;
                    loop65:
                    do {
                        int alt65=2;
                        int LA65_0 = input.LA(1);

                        if ( (LA65_0==Comma) ) {
                            alt65=1;
                        }


                        switch (alt65) {
                    	case 1 :
                    	    // InternalSqlParser.g:3231:2: otherlv_2= Comma ( (lv_entries_3_0= ruleOrderByColumnFull ) )
                    	    {
                    	    otherlv_2=(Token)match(input,Comma,FOLLOW_26); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_2, grammarAccess.getOrderByColumnsAccess().getCommaKeyword_1_1_0());
                    	          
                    	    }
                    	    // InternalSqlParser.g:3235:1: ( (lv_entries_3_0= ruleOrderByColumnFull ) )
                    	    // InternalSqlParser.g:3236:1: (lv_entries_3_0= ruleOrderByColumnFull )
                    	    {
                    	    // InternalSqlParser.g:3236:1: (lv_entries_3_0= ruleOrderByColumnFull )
                    	    // InternalSqlParser.g:3237:3: lv_entries_3_0= ruleOrderByColumnFull
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getOrderByColumnsAccess().getEntriesOrderByColumnFullParserRuleCall_1_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_9);
                    	    lv_entries_3_0=ruleOrderByColumnFull();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getOrderByColumnsRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"entries",
                    	              		lv_entries_3_0, 
                    	              		"com.jaspersoft.studio.data.Sql.OrderByColumnFull");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt65 >= 1 ) break loop65;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(65, input);
                                throw eee;
                        }
                        cnt65++;
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOrderByColumns"


    // $ANTLR start "entryRuleOrderByColumnFull"
    // InternalSqlParser.g:3261:1: entryRuleOrderByColumnFull returns [EObject current=null] : iv_ruleOrderByColumnFull= ruleOrderByColumnFull EOF ;
    public final EObject entryRuleOrderByColumnFull() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrderByColumnFull = null;


        try {
            // InternalSqlParser.g:3262:2: (iv_ruleOrderByColumnFull= ruleOrderByColumnFull EOF )
            // InternalSqlParser.g:3263:2: iv_ruleOrderByColumnFull= ruleOrderByColumnFull EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOrderByColumnFullRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOrderByColumnFull=ruleOrderByColumnFull();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOrderByColumnFull; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOrderByColumnFull"


    // $ANTLR start "ruleOrderByColumnFull"
    // InternalSqlParser.g:3270:1: ruleOrderByColumnFull returns [EObject current=null] : ( ( ( (lv_colOrder_0_0= ruleColumnFull ) ) | ( (lv_colOrderInt_1_0= RULE_UNSIGNED ) ) ) ( ( (lv_direction_2_1= ASC | lv_direction_2_2= DESC ) ) )? ) ;
    public final EObject ruleOrderByColumnFull() throws RecognitionException {
        EObject current = null;

        Token lv_colOrderInt_1_0=null;
        Token lv_direction_2_1=null;
        Token lv_direction_2_2=null;
        EObject lv_colOrder_0_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:3273:28: ( ( ( ( (lv_colOrder_0_0= ruleColumnFull ) ) | ( (lv_colOrderInt_1_0= RULE_UNSIGNED ) ) ) ( ( (lv_direction_2_1= ASC | lv_direction_2_2= DESC ) ) )? ) )
            // InternalSqlParser.g:3274:1: ( ( ( (lv_colOrder_0_0= ruleColumnFull ) ) | ( (lv_colOrderInt_1_0= RULE_UNSIGNED ) ) ) ( ( (lv_direction_2_1= ASC | lv_direction_2_2= DESC ) ) )? )
            {
            // InternalSqlParser.g:3274:1: ( ( ( (lv_colOrder_0_0= ruleColumnFull ) ) | ( (lv_colOrderInt_1_0= RULE_UNSIGNED ) ) ) ( ( (lv_direction_2_1= ASC | lv_direction_2_2= DESC ) ) )? )
            // InternalSqlParser.g:3274:2: ( ( (lv_colOrder_0_0= ruleColumnFull ) ) | ( (lv_colOrderInt_1_0= RULE_UNSIGNED ) ) ) ( ( (lv_direction_2_1= ASC | lv_direction_2_2= DESC ) ) )?
            {
            // InternalSqlParser.g:3274:2: ( ( (lv_colOrder_0_0= ruleColumnFull ) ) | ( (lv_colOrderInt_1_0= RULE_UNSIGNED ) ) )
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( ((LA67_0>=RULE_STRING && LA67_0<=RULE_ID)) ) {
                alt67=1;
            }
            else if ( (LA67_0==RULE_UNSIGNED) ) {
                alt67=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 67, 0, input);

                throw nvae;
            }
            switch (alt67) {
                case 1 :
                    // InternalSqlParser.g:3274:3: ( (lv_colOrder_0_0= ruleColumnFull ) )
                    {
                    // InternalSqlParser.g:3274:3: ( (lv_colOrder_0_0= ruleColumnFull ) )
                    // InternalSqlParser.g:3275:1: (lv_colOrder_0_0= ruleColumnFull )
                    {
                    // InternalSqlParser.g:3275:1: (lv_colOrder_0_0= ruleColumnFull )
                    // InternalSqlParser.g:3276:3: lv_colOrder_0_0= ruleColumnFull
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getOrderByColumnFullAccess().getColOrderColumnFullParserRuleCall_0_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_56);
                    lv_colOrder_0_0=ruleColumnFull();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getOrderByColumnFullRule());
                      	        }
                             		set(
                             			current, 
                             			"colOrder",
                              		lv_colOrder_0_0, 
                              		"com.jaspersoft.studio.data.Sql.ColumnFull");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:3293:6: ( (lv_colOrderInt_1_0= RULE_UNSIGNED ) )
                    {
                    // InternalSqlParser.g:3293:6: ( (lv_colOrderInt_1_0= RULE_UNSIGNED ) )
                    // InternalSqlParser.g:3294:1: (lv_colOrderInt_1_0= RULE_UNSIGNED )
                    {
                    // InternalSqlParser.g:3294:1: (lv_colOrderInt_1_0= RULE_UNSIGNED )
                    // InternalSqlParser.g:3295:3: lv_colOrderInt_1_0= RULE_UNSIGNED
                    {
                    lv_colOrderInt_1_0=(Token)match(input,RULE_UNSIGNED,FOLLOW_56); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(lv_colOrderInt_1_0, grammarAccess.getOrderByColumnFullAccess().getColOrderIntUNSIGNEDTerminalRuleCall_0_1_0()); 
                      		
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getOrderByColumnFullRule());
                      	        }
                             		setWithLastConsumed(
                             			current, 
                             			"colOrderInt",
                              		lv_colOrderInt_1_0, 
                              		"com.jaspersoft.studio.data.Sql.UNSIGNED");
                      	    
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalSqlParser.g:3311:3: ( ( (lv_direction_2_1= ASC | lv_direction_2_2= DESC ) ) )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==DESC||LA69_0==ASC) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // InternalSqlParser.g:3312:1: ( (lv_direction_2_1= ASC | lv_direction_2_2= DESC ) )
                    {
                    // InternalSqlParser.g:3312:1: ( (lv_direction_2_1= ASC | lv_direction_2_2= DESC ) )
                    // InternalSqlParser.g:3313:1: (lv_direction_2_1= ASC | lv_direction_2_2= DESC )
                    {
                    // InternalSqlParser.g:3313:1: (lv_direction_2_1= ASC | lv_direction_2_2= DESC )
                    int alt68=2;
                    int LA68_0 = input.LA(1);

                    if ( (LA68_0==ASC) ) {
                        alt68=1;
                    }
                    else if ( (LA68_0==DESC) ) {
                        alt68=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 68, 0, input);

                        throw nvae;
                    }
                    switch (alt68) {
                        case 1 :
                            // InternalSqlParser.g:3314:3: lv_direction_2_1= ASC
                            {
                            lv_direction_2_1=(Token)match(input,ASC,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                      newLeafNode(lv_direction_2_1, grammarAccess.getOrderByColumnFullAccess().getDirectionASCKeyword_1_0_0());
                                  
                            }
                            if ( state.backtracking==0 ) {

                              	        if (current==null) {
                              	            current = createModelElement(grammarAccess.getOrderByColumnFullRule());
                              	        }
                                     		setWithLastConsumed(current, "direction", lv_direction_2_1, null);
                              	    
                            }

                            }
                            break;
                        case 2 :
                            // InternalSqlParser.g:3327:8: lv_direction_2_2= DESC
                            {
                            lv_direction_2_2=(Token)match(input,DESC,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                      newLeafNode(lv_direction_2_2, grammarAccess.getOrderByColumnFullAccess().getDirectionDESCKeyword_1_0_1());
                                  
                            }
                            if ( state.backtracking==0 ) {

                              	        if (current==null) {
                              	            current = createModelElement(grammarAccess.getOrderByColumnFullRule());
                              	        }
                                     		setWithLastConsumed(current, "direction", lv_direction_2_2, null);
                              	    
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOrderByColumnFull"


    // $ANTLR start "entryRuleGroupByColumns"
    // InternalSqlParser.g:3351:1: entryRuleGroupByColumns returns [EObject current=null] : iv_ruleGroupByColumns= ruleGroupByColumns EOF ;
    public final EObject entryRuleGroupByColumns() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGroupByColumns = null;


        try {
            // InternalSqlParser.g:3352:2: (iv_ruleGroupByColumns= ruleGroupByColumns EOF )
            // InternalSqlParser.g:3353:2: iv_ruleGroupByColumns= ruleGroupByColumns EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getGroupByColumnsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleGroupByColumns=ruleGroupByColumns();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleGroupByColumns; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGroupByColumns"


    // $ANTLR start "ruleGroupByColumns"
    // InternalSqlParser.g:3360:1: ruleGroupByColumns returns [EObject current=null] : (this_GroupByColumnFull_0= ruleGroupByColumnFull ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleGroupByColumnFull ) ) )+ )? ) ;
    public final EObject ruleGroupByColumns() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_GroupByColumnFull_0 = null;

        EObject lv_entries_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:3363:28: ( (this_GroupByColumnFull_0= ruleGroupByColumnFull ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleGroupByColumnFull ) ) )+ )? ) )
            // InternalSqlParser.g:3364:1: (this_GroupByColumnFull_0= ruleGroupByColumnFull ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleGroupByColumnFull ) ) )+ )? )
            {
            // InternalSqlParser.g:3364:1: (this_GroupByColumnFull_0= ruleGroupByColumnFull ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleGroupByColumnFull ) ) )+ )? )
            // InternalSqlParser.g:3365:2: this_GroupByColumnFull_0= ruleGroupByColumnFull ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleGroupByColumnFull ) ) )+ )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getGroupByColumnsAccess().getGroupByColumnFullParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_9);
            this_GroupByColumnFull_0=ruleGroupByColumnFull();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_GroupByColumnFull_0;
                      afterParserOrEnumRuleCall();
                  
            }
            // InternalSqlParser.g:3376:1: ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleGroupByColumnFull ) ) )+ )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==Comma) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // InternalSqlParser.g:3376:2: () (otherlv_2= Comma ( (lv_entries_3_0= ruleGroupByColumnFull ) ) )+
                    {
                    // InternalSqlParser.g:3376:2: ()
                    // InternalSqlParser.g:3377:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getGroupByColumnsAccess().getOrGroupByColumnEntriesAction_1_0(),
                                  current);
                          
                    }

                    }

                    // InternalSqlParser.g:3385:2: (otherlv_2= Comma ( (lv_entries_3_0= ruleGroupByColumnFull ) ) )+
                    int cnt70=0;
                    loop70:
                    do {
                        int alt70=2;
                        int LA70_0 = input.LA(1);

                        if ( (LA70_0==Comma) ) {
                            alt70=1;
                        }


                        switch (alt70) {
                    	case 1 :
                    	    // InternalSqlParser.g:3386:2: otherlv_2= Comma ( (lv_entries_3_0= ruleGroupByColumnFull ) )
                    	    {
                    	    otherlv_2=(Token)match(input,Comma,FOLLOW_26); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_2, grammarAccess.getGroupByColumnsAccess().getCommaKeyword_1_1_0());
                    	          
                    	    }
                    	    // InternalSqlParser.g:3390:1: ( (lv_entries_3_0= ruleGroupByColumnFull ) )
                    	    // InternalSqlParser.g:3391:1: (lv_entries_3_0= ruleGroupByColumnFull )
                    	    {
                    	    // InternalSqlParser.g:3391:1: (lv_entries_3_0= ruleGroupByColumnFull )
                    	    // InternalSqlParser.g:3392:3: lv_entries_3_0= ruleGroupByColumnFull
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getGroupByColumnsAccess().getEntriesGroupByColumnFullParserRuleCall_1_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_9);
                    	    lv_entries_3_0=ruleGroupByColumnFull();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getGroupByColumnsRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"entries",
                    	              		lv_entries_3_0, 
                    	              		"com.jaspersoft.studio.data.Sql.GroupByColumnFull");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt70 >= 1 ) break loop70;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(70, input);
                                throw eee;
                        }
                        cnt70++;
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGroupByColumns"


    // $ANTLR start "entryRuleGroupByColumnFull"
    // InternalSqlParser.g:3416:1: entryRuleGroupByColumnFull returns [EObject current=null] : iv_ruleGroupByColumnFull= ruleGroupByColumnFull EOF ;
    public final EObject entryRuleGroupByColumnFull() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGroupByColumnFull = null;


        try {
            // InternalSqlParser.g:3417:2: (iv_ruleGroupByColumnFull= ruleGroupByColumnFull EOF )
            // InternalSqlParser.g:3418:2: iv_ruleGroupByColumnFull= ruleGroupByColumnFull EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getGroupByColumnFullRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleGroupByColumnFull=ruleGroupByColumnFull();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleGroupByColumnFull; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGroupByColumnFull"


    // $ANTLR start "ruleGroupByColumnFull"
    // InternalSqlParser.g:3425:1: ruleGroupByColumnFull returns [EObject current=null] : ( ( (lv_colGrBy_0_0= ruleColumnFull ) ) | ( (lv_gbFunction_1_0= ruleOperandFunction ) ) | ( (lv_grByInt_2_0= RULE_UNSIGNED ) ) ) ;
    public final EObject ruleGroupByColumnFull() throws RecognitionException {
        EObject current = null;

        Token lv_grByInt_2_0=null;
        EObject lv_colGrBy_0_0 = null;

        EObject lv_gbFunction_1_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:3428:28: ( ( ( (lv_colGrBy_0_0= ruleColumnFull ) ) | ( (lv_gbFunction_1_0= ruleOperandFunction ) ) | ( (lv_grByInt_2_0= RULE_UNSIGNED ) ) ) )
            // InternalSqlParser.g:3429:1: ( ( (lv_colGrBy_0_0= ruleColumnFull ) ) | ( (lv_gbFunction_1_0= ruleOperandFunction ) ) | ( (lv_grByInt_2_0= RULE_UNSIGNED ) ) )
            {
            // InternalSqlParser.g:3429:1: ( ( (lv_colGrBy_0_0= ruleColumnFull ) ) | ( (lv_gbFunction_1_0= ruleOperandFunction ) ) | ( (lv_grByInt_2_0= RULE_UNSIGNED ) ) )
            int alt72=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA72_1 = input.LA(2);

                if ( (LA72_1==LeftParenthesis) ) {
                    alt72=2;
                }
                else if ( (LA72_1==EOF||LA72_1==INTERSECT||LA72_1==EXCEPT||LA72_1==HAVING||LA72_1==OFFSET||LA72_1==FETCH||(LA72_1>=LIMIT && LA72_1<=MINUS)||LA72_1==ORDER||LA72_1==UNION||LA72_1==RightParenthesis||LA72_1==Comma||LA72_1==FullStop) ) {
                    alt72=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 72, 1, input);

                    throw nvae;
                }
                }
                break;
            case RULE_STRING:
            case RULE_DBNAME:
                {
                alt72=1;
                }
                break;
            case RULE_UNSIGNED:
                {
                alt72=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 72, 0, input);

                throw nvae;
            }

            switch (alt72) {
                case 1 :
                    // InternalSqlParser.g:3429:2: ( (lv_colGrBy_0_0= ruleColumnFull ) )
                    {
                    // InternalSqlParser.g:3429:2: ( (lv_colGrBy_0_0= ruleColumnFull ) )
                    // InternalSqlParser.g:3430:1: (lv_colGrBy_0_0= ruleColumnFull )
                    {
                    // InternalSqlParser.g:3430:1: (lv_colGrBy_0_0= ruleColumnFull )
                    // InternalSqlParser.g:3431:3: lv_colGrBy_0_0= ruleColumnFull
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getGroupByColumnFullAccess().getColGrByColumnFullParserRuleCall_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_colGrBy_0_0=ruleColumnFull();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getGroupByColumnFullRule());
                      	        }
                             		set(
                             			current, 
                             			"colGrBy",
                              		lv_colGrBy_0_0, 
                              		"com.jaspersoft.studio.data.Sql.ColumnFull");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:3448:6: ( (lv_gbFunction_1_0= ruleOperandFunction ) )
                    {
                    // InternalSqlParser.g:3448:6: ( (lv_gbFunction_1_0= ruleOperandFunction ) )
                    // InternalSqlParser.g:3449:1: (lv_gbFunction_1_0= ruleOperandFunction )
                    {
                    // InternalSqlParser.g:3449:1: (lv_gbFunction_1_0= ruleOperandFunction )
                    // InternalSqlParser.g:3450:3: lv_gbFunction_1_0= ruleOperandFunction
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getGroupByColumnFullAccess().getGbFunctionOperandFunctionParserRuleCall_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_gbFunction_1_0=ruleOperandFunction();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getGroupByColumnFullRule());
                      	        }
                             		set(
                             			current, 
                             			"gbFunction",
                              		lv_gbFunction_1_0, 
                              		"com.jaspersoft.studio.data.Sql.OperandFunction");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSqlParser.g:3467:6: ( (lv_grByInt_2_0= RULE_UNSIGNED ) )
                    {
                    // InternalSqlParser.g:3467:6: ( (lv_grByInt_2_0= RULE_UNSIGNED ) )
                    // InternalSqlParser.g:3468:1: (lv_grByInt_2_0= RULE_UNSIGNED )
                    {
                    // InternalSqlParser.g:3468:1: (lv_grByInt_2_0= RULE_UNSIGNED )
                    // InternalSqlParser.g:3469:3: lv_grByInt_2_0= RULE_UNSIGNED
                    {
                    lv_grByInt_2_0=(Token)match(input,RULE_UNSIGNED,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(lv_grByInt_2_0, grammarAccess.getGroupByColumnFullAccess().getGrByIntUNSIGNEDTerminalRuleCall_2_0()); 
                      		
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getGroupByColumnFullRule());
                      	        }
                             		setWithLastConsumed(
                             			current, 
                             			"grByInt",
                              		lv_grByInt_2_0, 
                              		"com.jaspersoft.studio.data.Sql.UNSIGNED");
                      	    
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGroupByColumnFull"


    // $ANTLR start "entryRuleFullExpression"
    // InternalSqlParser.g:3493:1: entryRuleFullExpression returns [EObject current=null] : iv_ruleFullExpression= ruleFullExpression EOF ;
    public final EObject entryRuleFullExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFullExpression = null;


        try {
            // InternalSqlParser.g:3494:2: (iv_ruleFullExpression= ruleFullExpression EOF )
            // InternalSqlParser.g:3495:2: iv_ruleFullExpression= ruleFullExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFullExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleFullExpression=ruleFullExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFullExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFullExpression"


    // $ANTLR start "ruleFullExpression"
    // InternalSqlParser.g:3502:1: ruleFullExpression returns [EObject current=null] : (this_ExpressionFragment_0= ruleExpressionFragment ( () ( (lv_entries_2_0= ruleExpressionFragmentSecond ) )+ )? ) ;
    public final EObject ruleFullExpression() throws RecognitionException {
        EObject current = null;

        EObject this_ExpressionFragment_0 = null;

        EObject lv_entries_2_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:3505:28: ( (this_ExpressionFragment_0= ruleExpressionFragment ( () ( (lv_entries_2_0= ruleExpressionFragmentSecond ) )+ )? ) )
            // InternalSqlParser.g:3506:1: (this_ExpressionFragment_0= ruleExpressionFragment ( () ( (lv_entries_2_0= ruleExpressionFragmentSecond ) )+ )? )
            {
            // InternalSqlParser.g:3506:1: (this_ExpressionFragment_0= ruleExpressionFragment ( () ( (lv_entries_2_0= ruleExpressionFragmentSecond ) )+ )? )
            // InternalSqlParser.g:3507:2: this_ExpressionFragment_0= ruleExpressionFragment ( () ( (lv_entries_2_0= ruleExpressionFragmentSecond ) )+ )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getFullExpressionAccess().getExpressionFragmentParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_57);
            this_ExpressionFragment_0=ruleExpressionFragment();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_ExpressionFragment_0;
                      afterParserOrEnumRuleCall();
                  
            }
            // InternalSqlParser.g:3518:1: ( () ( (lv_entries_2_0= ruleExpressionFragmentSecond ) )+ )?
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==AND||LA74_0==OR||LA74_0==RULE_JRNPARAM) ) {
                alt74=1;
            }
            switch (alt74) {
                case 1 :
                    // InternalSqlParser.g:3518:2: () ( (lv_entries_2_0= ruleExpressionFragmentSecond ) )+
                    {
                    // InternalSqlParser.g:3518:2: ()
                    // InternalSqlParser.g:3519:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getFullExpressionAccess().getOrExprEntriesAction_1_0(),
                                  current);
                          
                    }

                    }

                    // InternalSqlParser.g:3527:2: ( (lv_entries_2_0= ruleExpressionFragmentSecond ) )+
                    int cnt73=0;
                    loop73:
                    do {
                        int alt73=2;
                        int LA73_0 = input.LA(1);

                        if ( (LA73_0==AND||LA73_0==OR||LA73_0==RULE_JRNPARAM) ) {
                            alt73=1;
                        }


                        switch (alt73) {
                    	case 1 :
                    	    // InternalSqlParser.g:3528:1: (lv_entries_2_0= ruleExpressionFragmentSecond )
                    	    {
                    	    // InternalSqlParser.g:3528:1: (lv_entries_2_0= ruleExpressionFragmentSecond )
                    	    // InternalSqlParser.g:3529:3: lv_entries_2_0= ruleExpressionFragmentSecond
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getFullExpressionAccess().getEntriesExpressionFragmentSecondParserRuleCall_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_57);
                    	    lv_entries_2_0=ruleExpressionFragmentSecond();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getFullExpressionRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"entries",
                    	              		lv_entries_2_0, 
                    	              		"com.jaspersoft.studio.data.Sql.ExpressionFragmentSecond");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt73 >= 1 ) break loop73;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(73, input);
                                throw eee;
                        }
                        cnt73++;
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFullExpression"


    // $ANTLR start "entryRuleExpressionFragmentSecond"
    // InternalSqlParser.g:3553:1: entryRuleExpressionFragmentSecond returns [EObject current=null] : iv_ruleExpressionFragmentSecond= ruleExpressionFragmentSecond EOF ;
    public final EObject entryRuleExpressionFragmentSecond() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionFragmentSecond = null;


        try {
            // InternalSqlParser.g:3554:2: (iv_ruleExpressionFragmentSecond= ruleExpressionFragmentSecond EOF )
            // InternalSqlParser.g:3555:2: iv_ruleExpressionFragmentSecond= ruleExpressionFragmentSecond EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExpressionFragmentSecondRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleExpressionFragmentSecond=ruleExpressionFragmentSecond();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleExpressionFragmentSecond; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionFragmentSecond"


    // $ANTLR start "ruleExpressionFragmentSecond"
    // InternalSqlParser.g:3562:1: ruleExpressionFragmentSecond returns [EObject current=null] : ( ( ( ( (lv_c_0_1= AND | lv_c_0_2= OR ) ) ) ( (lv_efrag_1_0= ruleExpressionFragment ) ) ) | ( (lv_notPrm_2_0= RULE_JRNPARAM ) ) ) ;
    public final EObject ruleExpressionFragmentSecond() throws RecognitionException {
        EObject current = null;

        Token lv_c_0_1=null;
        Token lv_c_0_2=null;
        Token lv_notPrm_2_0=null;
        EObject lv_efrag_1_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:3565:28: ( ( ( ( ( (lv_c_0_1= AND | lv_c_0_2= OR ) ) ) ( (lv_efrag_1_0= ruleExpressionFragment ) ) ) | ( (lv_notPrm_2_0= RULE_JRNPARAM ) ) ) )
            // InternalSqlParser.g:3566:1: ( ( ( ( (lv_c_0_1= AND | lv_c_0_2= OR ) ) ) ( (lv_efrag_1_0= ruleExpressionFragment ) ) ) | ( (lv_notPrm_2_0= RULE_JRNPARAM ) ) )
            {
            // InternalSqlParser.g:3566:1: ( ( ( ( (lv_c_0_1= AND | lv_c_0_2= OR ) ) ) ( (lv_efrag_1_0= ruleExpressionFragment ) ) ) | ( (lv_notPrm_2_0= RULE_JRNPARAM ) ) )
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==AND||LA76_0==OR) ) {
                alt76=1;
            }
            else if ( (LA76_0==RULE_JRNPARAM) ) {
                alt76=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 76, 0, input);

                throw nvae;
            }
            switch (alt76) {
                case 1 :
                    // InternalSqlParser.g:3566:2: ( ( ( (lv_c_0_1= AND | lv_c_0_2= OR ) ) ) ( (lv_efrag_1_0= ruleExpressionFragment ) ) )
                    {
                    // InternalSqlParser.g:3566:2: ( ( ( (lv_c_0_1= AND | lv_c_0_2= OR ) ) ) ( (lv_efrag_1_0= ruleExpressionFragment ) ) )
                    // InternalSqlParser.g:3566:3: ( ( (lv_c_0_1= AND | lv_c_0_2= OR ) ) ) ( (lv_efrag_1_0= ruleExpressionFragment ) )
                    {
                    // InternalSqlParser.g:3566:3: ( ( (lv_c_0_1= AND | lv_c_0_2= OR ) ) )
                    // InternalSqlParser.g:3567:1: ( (lv_c_0_1= AND | lv_c_0_2= OR ) )
                    {
                    // InternalSqlParser.g:3567:1: ( (lv_c_0_1= AND | lv_c_0_2= OR ) )
                    // InternalSqlParser.g:3568:1: (lv_c_0_1= AND | lv_c_0_2= OR )
                    {
                    // InternalSqlParser.g:3568:1: (lv_c_0_1= AND | lv_c_0_2= OR )
                    int alt75=2;
                    int LA75_0 = input.LA(1);

                    if ( (LA75_0==AND) ) {
                        alt75=1;
                    }
                    else if ( (LA75_0==OR) ) {
                        alt75=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 75, 0, input);

                        throw nvae;
                    }
                    switch (alt75) {
                        case 1 :
                            // InternalSqlParser.g:3569:3: lv_c_0_1= AND
                            {
                            lv_c_0_1=(Token)match(input,AND,FOLLOW_23); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                      newLeafNode(lv_c_0_1, grammarAccess.getExpressionFragmentSecondAccess().getCANDKeyword_0_0_0_0());
                                  
                            }
                            if ( state.backtracking==0 ) {

                              	        if (current==null) {
                              	            current = createModelElement(grammarAccess.getExpressionFragmentSecondRule());
                              	        }
                                     		setWithLastConsumed(current, "c", lv_c_0_1, null);
                              	    
                            }

                            }
                            break;
                        case 2 :
                            // InternalSqlParser.g:3582:8: lv_c_0_2= OR
                            {
                            lv_c_0_2=(Token)match(input,OR,FOLLOW_23); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                      newLeafNode(lv_c_0_2, grammarAccess.getExpressionFragmentSecondAccess().getCORKeyword_0_0_0_1());
                                  
                            }
                            if ( state.backtracking==0 ) {

                              	        if (current==null) {
                              	            current = createModelElement(grammarAccess.getExpressionFragmentSecondRule());
                              	        }
                                     		setWithLastConsumed(current, "c", lv_c_0_2, null);
                              	    
                            }

                            }
                            break;

                    }


                    }


                    }

                    // InternalSqlParser.g:3598:2: ( (lv_efrag_1_0= ruleExpressionFragment ) )
                    // InternalSqlParser.g:3599:1: (lv_efrag_1_0= ruleExpressionFragment )
                    {
                    // InternalSqlParser.g:3599:1: (lv_efrag_1_0= ruleExpressionFragment )
                    // InternalSqlParser.g:3600:3: lv_efrag_1_0= ruleExpressionFragment
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getExpressionFragmentSecondAccess().getEfragExpressionFragmentParserRuleCall_0_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_efrag_1_0=ruleExpressionFragment();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getExpressionFragmentSecondRule());
                      	        }
                             		set(
                             			current, 
                             			"efrag",
                              		lv_efrag_1_0, 
                              		"com.jaspersoft.studio.data.Sql.ExpressionFragment");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:3617:6: ( (lv_notPrm_2_0= RULE_JRNPARAM ) )
                    {
                    // InternalSqlParser.g:3617:6: ( (lv_notPrm_2_0= RULE_JRNPARAM ) )
                    // InternalSqlParser.g:3618:1: (lv_notPrm_2_0= RULE_JRNPARAM )
                    {
                    // InternalSqlParser.g:3618:1: (lv_notPrm_2_0= RULE_JRNPARAM )
                    // InternalSqlParser.g:3619:3: lv_notPrm_2_0= RULE_JRNPARAM
                    {
                    lv_notPrm_2_0=(Token)match(input,RULE_JRNPARAM,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(lv_notPrm_2_0, grammarAccess.getExpressionFragmentSecondAccess().getNotPrmJRNPARAMTerminalRuleCall_1_0()); 
                      		
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getExpressionFragmentSecondRule());
                      	        }
                             		setWithLastConsumed(
                             			current, 
                             			"notPrm",
                              		lv_notPrm_2_0, 
                              		"com.jaspersoft.studio.data.Sql.JRNPARAM");
                      	    
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionFragmentSecond"


    // $ANTLR start "entryRuleExpressionFragment"
    // InternalSqlParser.g:3643:1: entryRuleExpressionFragment returns [EObject current=null] : iv_ruleExpressionFragment= ruleExpressionFragment EOF ;
    public final EObject entryRuleExpressionFragment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionFragment = null;


        try {
            // InternalSqlParser.g:3644:2: (iv_ruleExpressionFragment= ruleExpressionFragment EOF )
            // InternalSqlParser.g:3645:2: iv_ruleExpressionFragment= ruleExpressionFragment EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExpressionFragmentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleExpressionFragment=ruleExpressionFragment();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleExpressionFragment; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionFragment"


    // $ANTLR start "ruleExpressionFragment"
    // InternalSqlParser.g:3652:1: ruleExpressionFragment returns [EObject current=null] : ( ( (lv_expgroup_0_0= ruleExpressionGroup ) ) | ( (lv_exp_1_0= ruleExpression ) ) | ( ( (lv_xexp_2_1= ruleXExpression | lv_xexp_2_2= ruleXExpression_ ) ) ) | ( (lv_notPrm_3_0= RULE_JRNPARAM ) ) | ( (lv_in_4_0= ruleInOperator ) ) | ( (lv_exists_5_0= ruleExistsOperator ) ) ) ;
    public final EObject ruleExpressionFragment() throws RecognitionException {
        EObject current = null;

        Token lv_notPrm_3_0=null;
        EObject lv_expgroup_0_0 = null;

        EObject lv_exp_1_0 = null;

        EObject lv_xexp_2_1 = null;

        EObject lv_xexp_2_2 = null;

        EObject lv_in_4_0 = null;

        EObject lv_exists_5_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:3655:28: ( ( ( (lv_expgroup_0_0= ruleExpressionGroup ) ) | ( (lv_exp_1_0= ruleExpression ) ) | ( ( (lv_xexp_2_1= ruleXExpression | lv_xexp_2_2= ruleXExpression_ ) ) ) | ( (lv_notPrm_3_0= RULE_JRNPARAM ) ) | ( (lv_in_4_0= ruleInOperator ) ) | ( (lv_exists_5_0= ruleExistsOperator ) ) ) )
            // InternalSqlParser.g:3656:1: ( ( (lv_expgroup_0_0= ruleExpressionGroup ) ) | ( (lv_exp_1_0= ruleExpression ) ) | ( ( (lv_xexp_2_1= ruleXExpression | lv_xexp_2_2= ruleXExpression_ ) ) ) | ( (lv_notPrm_3_0= RULE_JRNPARAM ) ) | ( (lv_in_4_0= ruleInOperator ) ) | ( (lv_exists_5_0= ruleExistsOperator ) ) )
            {
            // InternalSqlParser.g:3656:1: ( ( (lv_expgroup_0_0= ruleExpressionGroup ) ) | ( (lv_exp_1_0= ruleExpression ) ) | ( ( (lv_xexp_2_1= ruleXExpression | lv_xexp_2_2= ruleXExpression_ ) ) ) | ( (lv_notPrm_3_0= RULE_JRNPARAM ) ) | ( (lv_in_4_0= ruleInOperator ) ) | ( (lv_exists_5_0= ruleExistsOperator ) ) )
            int alt78=6;
            alt78 = dfa78.predict(input);
            switch (alt78) {
                case 1 :
                    // InternalSqlParser.g:3656:2: ( (lv_expgroup_0_0= ruleExpressionGroup ) )
                    {
                    // InternalSqlParser.g:3656:2: ( (lv_expgroup_0_0= ruleExpressionGroup ) )
                    // InternalSqlParser.g:3657:1: (lv_expgroup_0_0= ruleExpressionGroup )
                    {
                    // InternalSqlParser.g:3657:1: (lv_expgroup_0_0= ruleExpressionGroup )
                    // InternalSqlParser.g:3658:3: lv_expgroup_0_0= ruleExpressionGroup
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getExpressionFragmentAccess().getExpgroupExpressionGroupParserRuleCall_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_expgroup_0_0=ruleExpressionGroup();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getExpressionFragmentRule());
                      	        }
                             		set(
                             			current, 
                             			"expgroup",
                              		lv_expgroup_0_0, 
                              		"com.jaspersoft.studio.data.Sql.ExpressionGroup");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:3675:6: ( (lv_exp_1_0= ruleExpression ) )
                    {
                    // InternalSqlParser.g:3675:6: ( (lv_exp_1_0= ruleExpression ) )
                    // InternalSqlParser.g:3676:1: (lv_exp_1_0= ruleExpression )
                    {
                    // InternalSqlParser.g:3676:1: (lv_exp_1_0= ruleExpression )
                    // InternalSqlParser.g:3677:3: lv_exp_1_0= ruleExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getExpressionFragmentAccess().getExpExpressionParserRuleCall_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_exp_1_0=ruleExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getExpressionFragmentRule());
                      	        }
                             		set(
                             			current, 
                             			"exp",
                              		lv_exp_1_0, 
                              		"com.jaspersoft.studio.data.Sql.Expression");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSqlParser.g:3694:6: ( ( (lv_xexp_2_1= ruleXExpression | lv_xexp_2_2= ruleXExpression_ ) ) )
                    {
                    // InternalSqlParser.g:3694:6: ( ( (lv_xexp_2_1= ruleXExpression | lv_xexp_2_2= ruleXExpression_ ) ) )
                    // InternalSqlParser.g:3695:1: ( (lv_xexp_2_1= ruleXExpression | lv_xexp_2_2= ruleXExpression_ ) )
                    {
                    // InternalSqlParser.g:3695:1: ( (lv_xexp_2_1= ruleXExpression | lv_xexp_2_2= ruleXExpression_ ) )
                    // InternalSqlParser.g:3696:1: (lv_xexp_2_1= ruleXExpression | lv_xexp_2_2= ruleXExpression_ )
                    {
                    // InternalSqlParser.g:3696:1: (lv_xexp_2_1= ruleXExpression | lv_xexp_2_2= ruleXExpression_ )
                    int alt77=2;
                    alt77 = dfa77.predict(input);
                    switch (alt77) {
                        case 1 :
                            // InternalSqlParser.g:3697:3: lv_xexp_2_1= ruleXExpression
                            {
                            if ( state.backtracking==0 ) {
                               
                              	        newCompositeNode(grammarAccess.getExpressionFragmentAccess().getXexpXExpressionParserRuleCall_2_0_0()); 
                              	    
                            }
                            pushFollow(FOLLOW_2);
                            lv_xexp_2_1=ruleXExpression();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              	        if (current==null) {
                              	            current = createModelElementForParent(grammarAccess.getExpressionFragmentRule());
                              	        }
                                     		set(
                                     			current, 
                                     			"xexp",
                                      		lv_xexp_2_1, 
                                      		"com.jaspersoft.studio.data.Sql.XExpression");
                              	        afterParserOrEnumRuleCall();
                              	    
                            }

                            }
                            break;
                        case 2 :
                            // InternalSqlParser.g:3712:8: lv_xexp_2_2= ruleXExpression_
                            {
                            if ( state.backtracking==0 ) {
                               
                              	        newCompositeNode(grammarAccess.getExpressionFragmentAccess().getXexpXExpression_ParserRuleCall_2_0_1()); 
                              	    
                            }
                            pushFollow(FOLLOW_2);
                            lv_xexp_2_2=ruleXExpression_();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              	        if (current==null) {
                              	            current = createModelElementForParent(grammarAccess.getExpressionFragmentRule());
                              	        }
                                     		set(
                                     			current, 
                                     			"xexp",
                                      		lv_xexp_2_2, 
                                      		"com.jaspersoft.studio.data.Sql.XExpression_");
                              	        afterParserOrEnumRuleCall();
                              	    
                            }

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalSqlParser.g:3731:6: ( (lv_notPrm_3_0= RULE_JRNPARAM ) )
                    {
                    // InternalSqlParser.g:3731:6: ( (lv_notPrm_3_0= RULE_JRNPARAM ) )
                    // InternalSqlParser.g:3732:1: (lv_notPrm_3_0= RULE_JRNPARAM )
                    {
                    // InternalSqlParser.g:3732:1: (lv_notPrm_3_0= RULE_JRNPARAM )
                    // InternalSqlParser.g:3733:3: lv_notPrm_3_0= RULE_JRNPARAM
                    {
                    lv_notPrm_3_0=(Token)match(input,RULE_JRNPARAM,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(lv_notPrm_3_0, grammarAccess.getExpressionFragmentAccess().getNotPrmJRNPARAMTerminalRuleCall_3_0()); 
                      		
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getExpressionFragmentRule());
                      	        }
                             		setWithLastConsumed(
                             			current, 
                             			"notPrm",
                              		lv_notPrm_3_0, 
                              		"com.jaspersoft.studio.data.Sql.JRNPARAM");
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalSqlParser.g:3750:6: ( (lv_in_4_0= ruleInOperator ) )
                    {
                    // InternalSqlParser.g:3750:6: ( (lv_in_4_0= ruleInOperator ) )
                    // InternalSqlParser.g:3751:1: (lv_in_4_0= ruleInOperator )
                    {
                    // InternalSqlParser.g:3751:1: (lv_in_4_0= ruleInOperator )
                    // InternalSqlParser.g:3752:3: lv_in_4_0= ruleInOperator
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getExpressionFragmentAccess().getInInOperatorParserRuleCall_4_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_in_4_0=ruleInOperator();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getExpressionFragmentRule());
                      	        }
                             		set(
                             			current, 
                             			"in",
                              		lv_in_4_0, 
                              		"com.jaspersoft.studio.data.Sql.InOperator");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalSqlParser.g:3769:6: ( (lv_exists_5_0= ruleExistsOperator ) )
                    {
                    // InternalSqlParser.g:3769:6: ( (lv_exists_5_0= ruleExistsOperator ) )
                    // InternalSqlParser.g:3770:1: (lv_exists_5_0= ruleExistsOperator )
                    {
                    // InternalSqlParser.g:3770:1: (lv_exists_5_0= ruleExistsOperator )
                    // InternalSqlParser.g:3771:3: lv_exists_5_0= ruleExistsOperator
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getExpressionFragmentAccess().getExistsExistsOperatorParserRuleCall_5_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_exists_5_0=ruleExistsOperator();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getExpressionFragmentRule());
                      	        }
                             		set(
                             			current, 
                             			"exists",
                              		lv_exists_5_0, 
                              		"com.jaspersoft.studio.data.Sql.ExistsOperator");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionFragment"


    // $ANTLR start "entryRuleExpressionGroup"
    // InternalSqlParser.g:3795:1: entryRuleExpressionGroup returns [EObject current=null] : iv_ruleExpressionGroup= ruleExpressionGroup EOF ;
    public final EObject entryRuleExpressionGroup() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionGroup = null;


        try {
            // InternalSqlParser.g:3796:2: (iv_ruleExpressionGroup= ruleExpressionGroup EOF )
            // InternalSqlParser.g:3797:2: iv_ruleExpressionGroup= ruleExpressionGroup EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExpressionGroupRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleExpressionGroup=ruleExpressionGroup();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleExpressionGroup; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionGroup"


    // $ANTLR start "ruleExpressionGroup"
    // InternalSqlParser.g:3804:1: ruleExpressionGroup returns [EObject current=null] : ( () ( ( (lv_isnot_1_1= NOT | lv_isnot_1_2= NOT_1 ) ) )? otherlv_2= LeftParenthesis ( (lv_expr_3_0= ruleFullExpression ) ) otherlv_4= RightParenthesis ) ;
    public final EObject ruleExpressionGroup() throws RecognitionException {
        EObject current = null;

        Token lv_isnot_1_1=null;
        Token lv_isnot_1_2=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_expr_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:3807:28: ( ( () ( ( (lv_isnot_1_1= NOT | lv_isnot_1_2= NOT_1 ) ) )? otherlv_2= LeftParenthesis ( (lv_expr_3_0= ruleFullExpression ) ) otherlv_4= RightParenthesis ) )
            // InternalSqlParser.g:3808:1: ( () ( ( (lv_isnot_1_1= NOT | lv_isnot_1_2= NOT_1 ) ) )? otherlv_2= LeftParenthesis ( (lv_expr_3_0= ruleFullExpression ) ) otherlv_4= RightParenthesis )
            {
            // InternalSqlParser.g:3808:1: ( () ( ( (lv_isnot_1_1= NOT | lv_isnot_1_2= NOT_1 ) ) )? otherlv_2= LeftParenthesis ( (lv_expr_3_0= ruleFullExpression ) ) otherlv_4= RightParenthesis )
            // InternalSqlParser.g:3808:2: () ( ( (lv_isnot_1_1= NOT | lv_isnot_1_2= NOT_1 ) ) )? otherlv_2= LeftParenthesis ( (lv_expr_3_0= ruleFullExpression ) ) otherlv_4= RightParenthesis
            {
            // InternalSqlParser.g:3808:2: ()
            // InternalSqlParser.g:3809:2: 
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getExpressionGroupAccess().getExprGroupAction_0(),
                          current);
                  
            }

            }

            // InternalSqlParser.g:3817:2: ( ( (lv_isnot_1_1= NOT | lv_isnot_1_2= NOT_1 ) ) )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==NOT_1||LA80_0==NOT) ) {
                alt80=1;
            }
            switch (alt80) {
                case 1 :
                    // InternalSqlParser.g:3818:1: ( (lv_isnot_1_1= NOT | lv_isnot_1_2= NOT_1 ) )
                    {
                    // InternalSqlParser.g:3818:1: ( (lv_isnot_1_1= NOT | lv_isnot_1_2= NOT_1 ) )
                    // InternalSqlParser.g:3819:1: (lv_isnot_1_1= NOT | lv_isnot_1_2= NOT_1 )
                    {
                    // InternalSqlParser.g:3819:1: (lv_isnot_1_1= NOT | lv_isnot_1_2= NOT_1 )
                    int alt79=2;
                    int LA79_0 = input.LA(1);

                    if ( (LA79_0==NOT) ) {
                        alt79=1;
                    }
                    else if ( (LA79_0==NOT_1) ) {
                        alt79=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 79, 0, input);

                        throw nvae;
                    }
                    switch (alt79) {
                        case 1 :
                            // InternalSqlParser.g:3820:3: lv_isnot_1_1= NOT
                            {
                            lv_isnot_1_1=(Token)match(input,NOT,FOLLOW_7); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                      newLeafNode(lv_isnot_1_1, grammarAccess.getExpressionGroupAccess().getIsnotNOTKeyword_1_0_0());
                                  
                            }
                            if ( state.backtracking==0 ) {

                              	        if (current==null) {
                              	            current = createModelElement(grammarAccess.getExpressionGroupRule());
                              	        }
                                     		setWithLastConsumed(current, "isnot", lv_isnot_1_1, null);
                              	    
                            }

                            }
                            break;
                        case 2 :
                            // InternalSqlParser.g:3833:8: lv_isnot_1_2= NOT_1
                            {
                            lv_isnot_1_2=(Token)match(input,NOT_1,FOLLOW_7); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                      newLeafNode(lv_isnot_1_2, grammarAccess.getExpressionGroupAccess().getIsnotNOTKeyword_1_0_1());
                                  
                            }
                            if ( state.backtracking==0 ) {

                              	        if (current==null) {
                              	            current = createModelElement(grammarAccess.getExpressionGroupRule());
                              	        }
                                     		setWithLastConsumed(current, "isnot", lv_isnot_1_2, null);
                              	    
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;

            }

            otherlv_2=(Token)match(input,LeftParenthesis,FOLLOW_23); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getExpressionGroupAccess().getLeftParenthesisKeyword_2());
                  
            }
            // InternalSqlParser.g:3854:1: ( (lv_expr_3_0= ruleFullExpression ) )
            // InternalSqlParser.g:3855:1: (lv_expr_3_0= ruleFullExpression )
            {
            // InternalSqlParser.g:3855:1: (lv_expr_3_0= ruleFullExpression )
            // InternalSqlParser.g:3856:3: lv_expr_3_0= ruleFullExpression
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getExpressionGroupAccess().getExprFullExpressionParserRuleCall_3_0()); 
              	    
            }
            pushFollow(FOLLOW_8);
            lv_expr_3_0=ruleFullExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getExpressionGroupRule());
              	        }
                     		set(
                     			current, 
                     			"expr",
                      		lv_expr_3_0, 
                      		"com.jaspersoft.studio.data.Sql.FullExpression");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_4=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_4, grammarAccess.getExpressionGroupAccess().getRightParenthesisKeyword_4());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionGroup"


    // $ANTLR start "entryRuleXExpression"
    // InternalSqlParser.g:3885:1: entryRuleXExpression returns [EObject current=null] : iv_ruleXExpression= ruleXExpression EOF ;
    public final EObject entryRuleXExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXExpression = null;


        try {
            // InternalSqlParser.g:3886:2: (iv_ruleXExpression= ruleXExpression EOF )
            // InternalSqlParser.g:3887:2: iv_ruleXExpression= ruleXExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXExpression=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXExpression"


    // $ANTLR start "ruleXExpression"
    // InternalSqlParser.g:3894:1: ruleXExpression returns [EObject current=null] : (otherlv_0= X () ( (lv_xf_2_0= ruleXFunction ) ) otherlv_3= Comma ( (lv_col_4_0= ruleOperandGroup ) ) (otherlv_5= Comma ( (lv_prm_6_0= ruleXExpressionParams ) ) )? otherlv_7= RightCurlyBracket ) ;
    public final EObject ruleXExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Enumerator lv_xf_2_0 = null;

        EObject lv_col_4_0 = null;

        EObject lv_prm_6_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:3897:28: ( (otherlv_0= X () ( (lv_xf_2_0= ruleXFunction ) ) otherlv_3= Comma ( (lv_col_4_0= ruleOperandGroup ) ) (otherlv_5= Comma ( (lv_prm_6_0= ruleXExpressionParams ) ) )? otherlv_7= RightCurlyBracket ) )
            // InternalSqlParser.g:3898:1: (otherlv_0= X () ( (lv_xf_2_0= ruleXFunction ) ) otherlv_3= Comma ( (lv_col_4_0= ruleOperandGroup ) ) (otherlv_5= Comma ( (lv_prm_6_0= ruleXExpressionParams ) ) )? otherlv_7= RightCurlyBracket )
            {
            // InternalSqlParser.g:3898:1: (otherlv_0= X () ( (lv_xf_2_0= ruleXFunction ) ) otherlv_3= Comma ( (lv_col_4_0= ruleOperandGroup ) ) (otherlv_5= Comma ( (lv_prm_6_0= ruleXExpressionParams ) ) )? otherlv_7= RightCurlyBracket )
            // InternalSqlParser.g:3899:2: otherlv_0= X () ( (lv_xf_2_0= ruleXFunction ) ) otherlv_3= Comma ( (lv_col_4_0= ruleOperandGroup ) ) (otherlv_5= Comma ( (lv_prm_6_0= ruleXExpressionParams ) ) )? otherlv_7= RightCurlyBracket
            {
            otherlv_0=(Token)match(input,X,FOLLOW_58); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getXExpressionAccess().getXKeyword_0());
                  
            }
            // InternalSqlParser.g:3903:1: ()
            // InternalSqlParser.g:3904:2: 
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getXExpressionAccess().getXExprAction_1(),
                          current);
                  
            }

            }

            // InternalSqlParser.g:3912:2: ( (lv_xf_2_0= ruleXFunction ) )
            // InternalSqlParser.g:3913:1: (lv_xf_2_0= ruleXFunction )
            {
            // InternalSqlParser.g:3913:1: (lv_xf_2_0= ruleXFunction )
            // InternalSqlParser.g:3914:3: lv_xf_2_0= ruleXFunction
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getXExpressionAccess().getXfXFunctionEnumRuleCall_2_0()); 
              	    
            }
            pushFollow(FOLLOW_59);
            lv_xf_2_0=ruleXFunction();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getXExpressionRule());
              	        }
                     		set(
                     			current, 
                     			"xf",
                      		lv_xf_2_0, 
                      		"com.jaspersoft.studio.data.Sql.XFunction");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_3=(Token)match(input,Comma,FOLLOW_60); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getXExpressionAccess().getCommaKeyword_3());
                  
            }
            // InternalSqlParser.g:3935:1: ( (lv_col_4_0= ruleOperandGroup ) )
            // InternalSqlParser.g:3936:1: (lv_col_4_0= ruleOperandGroup )
            {
            // InternalSqlParser.g:3936:1: (lv_col_4_0= ruleOperandGroup )
            // InternalSqlParser.g:3937:3: lv_col_4_0= ruleOperandGroup
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getXExpressionAccess().getColOperandGroupParserRuleCall_4_0()); 
              	    
            }
            pushFollow(FOLLOW_61);
            lv_col_4_0=ruleOperandGroup();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getXExpressionRule());
              	        }
                     		set(
                     			current, 
                     			"col",
                      		lv_col_4_0, 
                      		"com.jaspersoft.studio.data.Sql.OperandGroup");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:3953:2: (otherlv_5= Comma ( (lv_prm_6_0= ruleXExpressionParams ) ) )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==Comma) ) {
                alt81=1;
            }
            switch (alt81) {
                case 1 :
                    // InternalSqlParser.g:3954:2: otherlv_5= Comma ( (lv_prm_6_0= ruleXExpressionParams ) )
                    {
                    otherlv_5=(Token)match(input,Comma,FOLLOW_46); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_5, grammarAccess.getXExpressionAccess().getCommaKeyword_5_0());
                          
                    }
                    // InternalSqlParser.g:3958:1: ( (lv_prm_6_0= ruleXExpressionParams ) )
                    // InternalSqlParser.g:3959:1: (lv_prm_6_0= ruleXExpressionParams )
                    {
                    // InternalSqlParser.g:3959:1: (lv_prm_6_0= ruleXExpressionParams )
                    // InternalSqlParser.g:3960:3: lv_prm_6_0= ruleXExpressionParams
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getXExpressionAccess().getPrmXExpressionParamsParserRuleCall_5_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_62);
                    lv_prm_6_0=ruleXExpressionParams();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getXExpressionRule());
                      	        }
                             		set(
                             			current, 
                             			"prm",
                              		lv_prm_6_0, 
                              		"com.jaspersoft.studio.data.Sql.XExpressionParams");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_7=(Token)match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_7, grammarAccess.getXExpressionAccess().getRightCurlyBracketKeyword_6());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXExpression"


    // $ANTLR start "entryRuleXExpression_"
    // InternalSqlParser.g:3989:1: entryRuleXExpression_ returns [EObject current=null] : iv_ruleXExpression_= ruleXExpression_ EOF ;
    public final EObject entryRuleXExpression_() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXExpression_ = null;


        try {
            // InternalSqlParser.g:3990:2: (iv_ruleXExpression_= ruleXExpression_ EOF )
            // InternalSqlParser.g:3991:2: iv_ruleXExpression_= ruleXExpression_ EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXExpression_Rule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXExpression_=ruleXExpression_();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXExpression_; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXExpression_"


    // $ANTLR start "ruleXExpression_"
    // InternalSqlParser.g:3998:1: ruleXExpression_ returns [EObject current=null] : (otherlv_0= X () ( (lv_xf_2_0= ruleXFunction ) ) otherlv_3= VerticalLine ( (lv_col_4_0= ruleOperandGroup ) ) (otherlv_5= VerticalLine ( (lv_prm_6_0= ruleXExpressionParams ) ) )? otherlv_7= RightCurlyBracket ) ;
    public final EObject ruleXExpression_() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Enumerator lv_xf_2_0 = null;

        EObject lv_col_4_0 = null;

        EObject lv_prm_6_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:4001:28: ( (otherlv_0= X () ( (lv_xf_2_0= ruleXFunction ) ) otherlv_3= VerticalLine ( (lv_col_4_0= ruleOperandGroup ) ) (otherlv_5= VerticalLine ( (lv_prm_6_0= ruleXExpressionParams ) ) )? otherlv_7= RightCurlyBracket ) )
            // InternalSqlParser.g:4002:1: (otherlv_0= X () ( (lv_xf_2_0= ruleXFunction ) ) otherlv_3= VerticalLine ( (lv_col_4_0= ruleOperandGroup ) ) (otherlv_5= VerticalLine ( (lv_prm_6_0= ruleXExpressionParams ) ) )? otherlv_7= RightCurlyBracket )
            {
            // InternalSqlParser.g:4002:1: (otherlv_0= X () ( (lv_xf_2_0= ruleXFunction ) ) otherlv_3= VerticalLine ( (lv_col_4_0= ruleOperandGroup ) ) (otherlv_5= VerticalLine ( (lv_prm_6_0= ruleXExpressionParams ) ) )? otherlv_7= RightCurlyBracket )
            // InternalSqlParser.g:4003:2: otherlv_0= X () ( (lv_xf_2_0= ruleXFunction ) ) otherlv_3= VerticalLine ( (lv_col_4_0= ruleOperandGroup ) ) (otherlv_5= VerticalLine ( (lv_prm_6_0= ruleXExpressionParams ) ) )? otherlv_7= RightCurlyBracket
            {
            otherlv_0=(Token)match(input,X,FOLLOW_58); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getXExpression_Access().getXKeyword_0());
                  
            }
            // InternalSqlParser.g:4007:1: ()
            // InternalSqlParser.g:4008:2: 
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getXExpression_Access().getXExprAction_1(),
                          current);
                  
            }

            }

            // InternalSqlParser.g:4016:2: ( (lv_xf_2_0= ruleXFunction ) )
            // InternalSqlParser.g:4017:1: (lv_xf_2_0= ruleXFunction )
            {
            // InternalSqlParser.g:4017:1: (lv_xf_2_0= ruleXFunction )
            // InternalSqlParser.g:4018:3: lv_xf_2_0= ruleXFunction
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getXExpression_Access().getXfXFunctionEnumRuleCall_2_0()); 
              	    
            }
            pushFollow(FOLLOW_63);
            lv_xf_2_0=ruleXFunction();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getXExpression_Rule());
              	        }
                     		set(
                     			current, 
                     			"xf",
                      		lv_xf_2_0, 
                      		"com.jaspersoft.studio.data.Sql.XFunction");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_3=(Token)match(input,VerticalLine,FOLLOW_60); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getXExpression_Access().getVerticalLineKeyword_3());
                  
            }
            // InternalSqlParser.g:4039:1: ( (lv_col_4_0= ruleOperandGroup ) )
            // InternalSqlParser.g:4040:1: (lv_col_4_0= ruleOperandGroup )
            {
            // InternalSqlParser.g:4040:1: (lv_col_4_0= ruleOperandGroup )
            // InternalSqlParser.g:4041:3: lv_col_4_0= ruleOperandGroup
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getXExpression_Access().getColOperandGroupParserRuleCall_4_0()); 
              	    
            }
            pushFollow(FOLLOW_64);
            lv_col_4_0=ruleOperandGroup();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getXExpression_Rule());
              	        }
                     		set(
                     			current, 
                     			"col",
                      		lv_col_4_0, 
                      		"com.jaspersoft.studio.data.Sql.OperandGroup");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:4057:2: (otherlv_5= VerticalLine ( (lv_prm_6_0= ruleXExpressionParams ) ) )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==VerticalLine) ) {
                alt82=1;
            }
            switch (alt82) {
                case 1 :
                    // InternalSqlParser.g:4058:2: otherlv_5= VerticalLine ( (lv_prm_6_0= ruleXExpressionParams ) )
                    {
                    otherlv_5=(Token)match(input,VerticalLine,FOLLOW_46); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_5, grammarAccess.getXExpression_Access().getVerticalLineKeyword_5_0());
                          
                    }
                    // InternalSqlParser.g:4062:1: ( (lv_prm_6_0= ruleXExpressionParams ) )
                    // InternalSqlParser.g:4063:1: (lv_prm_6_0= ruleXExpressionParams )
                    {
                    // InternalSqlParser.g:4063:1: (lv_prm_6_0= ruleXExpressionParams )
                    // InternalSqlParser.g:4064:3: lv_prm_6_0= ruleXExpressionParams
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getXExpression_Access().getPrmXExpressionParamsParserRuleCall_5_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_62);
                    lv_prm_6_0=ruleXExpressionParams();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getXExpression_Rule());
                      	        }
                             		set(
                             			current, 
                             			"prm",
                              		lv_prm_6_0, 
                              		"com.jaspersoft.studio.data.Sql.XExpressionParams");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_7=(Token)match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_7, grammarAccess.getXExpression_Access().getRightCurlyBracketKeyword_6());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXExpression_"


    // $ANTLR start "entryRuleXExpressionParams"
    // InternalSqlParser.g:4093:1: entryRuleXExpressionParams returns [EObject current=null] : iv_ruleXExpressionParams= ruleXExpressionParams EOF ;
    public final EObject entryRuleXExpressionParams() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXExpressionParams = null;


        try {
            // InternalSqlParser.g:4094:2: (iv_ruleXExpressionParams= ruleXExpressionParams EOF )
            // InternalSqlParser.g:4095:2: iv_ruleXExpressionParams= ruleXExpressionParams EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXExpressionParamsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXExpressionParams=ruleXExpressionParams();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXExpressionParams; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXExpressionParams"


    // $ANTLR start "ruleXExpressionParams"
    // InternalSqlParser.g:4102:1: ruleXExpressionParams returns [EObject current=null] : (this_JRParameter_0= ruleJRParameter ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleJRParameter ) ) )+ )? ) ;
    public final EObject ruleXExpressionParams() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_JRParameter_0 = null;

        EObject lv_entries_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:4105:28: ( (this_JRParameter_0= ruleJRParameter ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleJRParameter ) ) )+ )? ) )
            // InternalSqlParser.g:4106:1: (this_JRParameter_0= ruleJRParameter ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleJRParameter ) ) )+ )? )
            {
            // InternalSqlParser.g:4106:1: (this_JRParameter_0= ruleJRParameter ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleJRParameter ) ) )+ )? )
            // InternalSqlParser.g:4107:2: this_JRParameter_0= ruleJRParameter ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleJRParameter ) ) )+ )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getXExpressionParamsAccess().getJRParameterParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_9);
            this_JRParameter_0=ruleJRParameter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_JRParameter_0;
                      afterParserOrEnumRuleCall();
                  
            }
            // InternalSqlParser.g:4118:1: ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleJRParameter ) ) )+ )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==Comma) ) {
                alt84=1;
            }
            switch (alt84) {
                case 1 :
                    // InternalSqlParser.g:4118:2: () (otherlv_2= Comma ( (lv_entries_3_0= ruleJRParameter ) ) )+
                    {
                    // InternalSqlParser.g:4118:2: ()
                    // InternalSqlParser.g:4119:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getXExpressionParamsAccess().getPrmsEntriesAction_1_0(),
                                  current);
                          
                    }

                    }

                    // InternalSqlParser.g:4127:2: (otherlv_2= Comma ( (lv_entries_3_0= ruleJRParameter ) ) )+
                    int cnt83=0;
                    loop83:
                    do {
                        int alt83=2;
                        int LA83_0 = input.LA(1);

                        if ( (LA83_0==Comma) ) {
                            alt83=1;
                        }


                        switch (alt83) {
                    	case 1 :
                    	    // InternalSqlParser.g:4128:2: otherlv_2= Comma ( (lv_entries_3_0= ruleJRParameter ) )
                    	    {
                    	    otherlv_2=(Token)match(input,Comma,FOLLOW_46); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_2, grammarAccess.getXExpressionParamsAccess().getCommaKeyword_1_1_0());
                    	          
                    	    }
                    	    // InternalSqlParser.g:4132:1: ( (lv_entries_3_0= ruleJRParameter ) )
                    	    // InternalSqlParser.g:4133:1: (lv_entries_3_0= ruleJRParameter )
                    	    {
                    	    // InternalSqlParser.g:4133:1: (lv_entries_3_0= ruleJRParameter )
                    	    // InternalSqlParser.g:4134:3: lv_entries_3_0= ruleJRParameter
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getXExpressionParamsAccess().getEntriesJRParameterParserRuleCall_1_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_9);
                    	    lv_entries_3_0=ruleJRParameter();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getXExpressionParamsRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"entries",
                    	              		lv_entries_3_0, 
                    	              		"com.jaspersoft.studio.data.Sql.JRParameter");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt83 >= 1 ) break loop83;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(83, input);
                                throw eee;
                        }
                        cnt83++;
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXExpressionParams"


    // $ANTLR start "entryRuleJRParameter"
    // InternalSqlParser.g:4158:1: entryRuleJRParameter returns [EObject current=null] : iv_ruleJRParameter= ruleJRParameter EOF ;
    public final EObject entryRuleJRParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJRParameter = null;


        try {
            // InternalSqlParser.g:4159:2: (iv_ruleJRParameter= ruleJRParameter EOF )
            // InternalSqlParser.g:4160:2: iv_ruleJRParameter= ruleJRParameter EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJRParameterRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleJRParameter=ruleJRParameter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJRParameter; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJRParameter"


    // $ANTLR start "ruleJRParameter"
    // InternalSqlParser.g:4167:1: ruleJRParameter returns [EObject current=null] : ( (lv_jrprm_0_0= RULE_ID ) ) ;
    public final EObject ruleJRParameter() throws RecognitionException {
        EObject current = null;

        Token lv_jrprm_0_0=null;

         enterRule(); 
            
        try {
            // InternalSqlParser.g:4170:28: ( ( (lv_jrprm_0_0= RULE_ID ) ) )
            // InternalSqlParser.g:4171:1: ( (lv_jrprm_0_0= RULE_ID ) )
            {
            // InternalSqlParser.g:4171:1: ( (lv_jrprm_0_0= RULE_ID ) )
            // InternalSqlParser.g:4172:1: (lv_jrprm_0_0= RULE_ID )
            {
            // InternalSqlParser.g:4172:1: (lv_jrprm_0_0= RULE_ID )
            // InternalSqlParser.g:4173:3: lv_jrprm_0_0= RULE_ID
            {
            lv_jrprm_0_0=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_jrprm_0_0, grammarAccess.getJRParameterAccess().getJrprmIDTerminalRuleCall_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getJRParameterRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"jrprm",
                      		lv_jrprm_0_0, 
                      		"com.jaspersoft.studio.data.Sql.ID");
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJRParameter"


    // $ANTLR start "entryRuleExpression"
    // InternalSqlParser.g:4197:1: entryRuleExpression returns [EObject current=null] : iv_ruleExpression= ruleExpression EOF ;
    public final EObject entryRuleExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpression = null;


        try {
            // InternalSqlParser.g:4198:2: (iv_ruleExpression= ruleExpression EOF )
            // InternalSqlParser.g:4199:2: iv_ruleExpression= ruleExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleExpression=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpression"


    // $ANTLR start "ruleExpression"
    // InternalSqlParser.g:4206:1: ruleExpression returns [EObject current=null] : ( ( (lv_op1_0_0= ruleOperand ) ) ( ( (lv_isnull_1_0= ruleIsNullValue ) ) | ( (lv_in_2_0= ruleInOperator ) ) | ( (lv_exists_3_0= ruleExistsOperator ) ) | ( (lv_between_4_0= ruleBetween ) ) | ( (lv_like_5_0= ruleLike ) ) | ( (lv_comp_6_0= ruleComparison ) ) ) ) ;
    public final EObject ruleExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_op1_0_0 = null;

        AntlrDatatypeRuleToken lv_isnull_1_0 = null;

        EObject lv_in_2_0 = null;

        EObject lv_exists_3_0 = null;

        EObject lv_between_4_0 = null;

        EObject lv_like_5_0 = null;

        EObject lv_comp_6_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:4209:28: ( ( ( (lv_op1_0_0= ruleOperand ) ) ( ( (lv_isnull_1_0= ruleIsNullValue ) ) | ( (lv_in_2_0= ruleInOperator ) ) | ( (lv_exists_3_0= ruleExistsOperator ) ) | ( (lv_between_4_0= ruleBetween ) ) | ( (lv_like_5_0= ruleLike ) ) | ( (lv_comp_6_0= ruleComparison ) ) ) ) )
            // InternalSqlParser.g:4210:1: ( ( (lv_op1_0_0= ruleOperand ) ) ( ( (lv_isnull_1_0= ruleIsNullValue ) ) | ( (lv_in_2_0= ruleInOperator ) ) | ( (lv_exists_3_0= ruleExistsOperator ) ) | ( (lv_between_4_0= ruleBetween ) ) | ( (lv_like_5_0= ruleLike ) ) | ( (lv_comp_6_0= ruleComparison ) ) ) )
            {
            // InternalSqlParser.g:4210:1: ( ( (lv_op1_0_0= ruleOperand ) ) ( ( (lv_isnull_1_0= ruleIsNullValue ) ) | ( (lv_in_2_0= ruleInOperator ) ) | ( (lv_exists_3_0= ruleExistsOperator ) ) | ( (lv_between_4_0= ruleBetween ) ) | ( (lv_like_5_0= ruleLike ) ) | ( (lv_comp_6_0= ruleComparison ) ) ) )
            // InternalSqlParser.g:4210:2: ( (lv_op1_0_0= ruleOperand ) ) ( ( (lv_isnull_1_0= ruleIsNullValue ) ) | ( (lv_in_2_0= ruleInOperator ) ) | ( (lv_exists_3_0= ruleExistsOperator ) ) | ( (lv_between_4_0= ruleBetween ) ) | ( (lv_like_5_0= ruleLike ) ) | ( (lv_comp_6_0= ruleComparison ) ) )
            {
            // InternalSqlParser.g:4210:2: ( (lv_op1_0_0= ruleOperand ) )
            // InternalSqlParser.g:4211:1: (lv_op1_0_0= ruleOperand )
            {
            // InternalSqlParser.g:4211:1: (lv_op1_0_0= ruleOperand )
            // InternalSqlParser.g:4212:3: lv_op1_0_0= ruleOperand
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getExpressionAccess().getOp1OperandParserRuleCall_0_0()); 
              	    
            }
            pushFollow(FOLLOW_65);
            lv_op1_0_0=ruleOperand();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getExpressionRule());
              	        }
                     		set(
                     			current, 
                     			"op1",
                      		lv_op1_0_0, 
                      		"com.jaspersoft.studio.data.Sql.Operand");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:4228:2: ( ( (lv_isnull_1_0= ruleIsNullValue ) ) | ( (lv_in_2_0= ruleInOperator ) ) | ( (lv_exists_3_0= ruleExistsOperator ) ) | ( (lv_between_4_0= ruleBetween ) ) | ( (lv_like_5_0= ruleLike ) ) | ( (lv_comp_6_0= ruleComparison ) ) )
            int alt85=6;
            switch ( input.LA(1) ) {
            case IS:
                {
                alt85=1;
                }
                break;
            case NOT:
                {
                switch ( input.LA(2) ) {
                case EXISTS:
                    {
                    alt85=3;
                    }
                    break;
                case LIKE:
                    {
                    alt85=5;
                    }
                    break;
                case IN:
                    {
                    alt85=2;
                    }
                    break;
                case BETWEEN:
                    {
                    alt85=4;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 85, 2, input);

                    throw nvae;
                }

                }
                break;
            case IN:
                {
                alt85=2;
                }
                break;
            case EXISTS:
                {
                alt85=3;
                }
                break;
            case BETWEEN:
                {
                alt85=4;
                }
                break;
            case LIKE:
                {
                alt85=5;
                }
                break;
            case ExclamationMarkEqualsSign:
            case LessThanSignEqualsSign:
            case LessThanSignGreaterThanSign:
            case GreaterThanSignEqualsSign:
            case CircumflexAccentEqualsSign:
            case LessThanSign:
            case EqualsSign:
            case GreaterThanSign:
                {
                alt85=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 85, 0, input);

                throw nvae;
            }

            switch (alt85) {
                case 1 :
                    // InternalSqlParser.g:4228:3: ( (lv_isnull_1_0= ruleIsNullValue ) )
                    {
                    // InternalSqlParser.g:4228:3: ( (lv_isnull_1_0= ruleIsNullValue ) )
                    // InternalSqlParser.g:4229:1: (lv_isnull_1_0= ruleIsNullValue )
                    {
                    // InternalSqlParser.g:4229:1: (lv_isnull_1_0= ruleIsNullValue )
                    // InternalSqlParser.g:4230:3: lv_isnull_1_0= ruleIsNullValue
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getExpressionAccess().getIsnullIsNullValueParserRuleCall_1_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_isnull_1_0=ruleIsNullValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getExpressionRule());
                      	        }
                             		set(
                             			current, 
                             			"isnull",
                              		lv_isnull_1_0, 
                              		"com.jaspersoft.studio.data.Sql.IsNullValue");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:4247:6: ( (lv_in_2_0= ruleInOperator ) )
                    {
                    // InternalSqlParser.g:4247:6: ( (lv_in_2_0= ruleInOperator ) )
                    // InternalSqlParser.g:4248:1: (lv_in_2_0= ruleInOperator )
                    {
                    // InternalSqlParser.g:4248:1: (lv_in_2_0= ruleInOperator )
                    // InternalSqlParser.g:4249:3: lv_in_2_0= ruleInOperator
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getExpressionAccess().getInInOperatorParserRuleCall_1_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_in_2_0=ruleInOperator();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getExpressionRule());
                      	        }
                             		set(
                             			current, 
                             			"in",
                              		lv_in_2_0, 
                              		"com.jaspersoft.studio.data.Sql.InOperator");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSqlParser.g:4266:6: ( (lv_exists_3_0= ruleExistsOperator ) )
                    {
                    // InternalSqlParser.g:4266:6: ( (lv_exists_3_0= ruleExistsOperator ) )
                    // InternalSqlParser.g:4267:1: (lv_exists_3_0= ruleExistsOperator )
                    {
                    // InternalSqlParser.g:4267:1: (lv_exists_3_0= ruleExistsOperator )
                    // InternalSqlParser.g:4268:3: lv_exists_3_0= ruleExistsOperator
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getExpressionAccess().getExistsExistsOperatorParserRuleCall_1_2_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_exists_3_0=ruleExistsOperator();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getExpressionRule());
                      	        }
                             		set(
                             			current, 
                             			"exists",
                              		lv_exists_3_0, 
                              		"com.jaspersoft.studio.data.Sql.ExistsOperator");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalSqlParser.g:4285:6: ( (lv_between_4_0= ruleBetween ) )
                    {
                    // InternalSqlParser.g:4285:6: ( (lv_between_4_0= ruleBetween ) )
                    // InternalSqlParser.g:4286:1: (lv_between_4_0= ruleBetween )
                    {
                    // InternalSqlParser.g:4286:1: (lv_between_4_0= ruleBetween )
                    // InternalSqlParser.g:4287:3: lv_between_4_0= ruleBetween
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getExpressionAccess().getBetweenBetweenParserRuleCall_1_3_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_between_4_0=ruleBetween();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getExpressionRule());
                      	        }
                             		set(
                             			current, 
                             			"between",
                              		lv_between_4_0, 
                              		"com.jaspersoft.studio.data.Sql.Between");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalSqlParser.g:4304:6: ( (lv_like_5_0= ruleLike ) )
                    {
                    // InternalSqlParser.g:4304:6: ( (lv_like_5_0= ruleLike ) )
                    // InternalSqlParser.g:4305:1: (lv_like_5_0= ruleLike )
                    {
                    // InternalSqlParser.g:4305:1: (lv_like_5_0= ruleLike )
                    // InternalSqlParser.g:4306:3: lv_like_5_0= ruleLike
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getExpressionAccess().getLikeLikeParserRuleCall_1_4_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_like_5_0=ruleLike();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getExpressionRule());
                      	        }
                             		set(
                             			current, 
                             			"like",
                              		lv_like_5_0, 
                              		"com.jaspersoft.studio.data.Sql.Like");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalSqlParser.g:4323:6: ( (lv_comp_6_0= ruleComparison ) )
                    {
                    // InternalSqlParser.g:4323:6: ( (lv_comp_6_0= ruleComparison ) )
                    // InternalSqlParser.g:4324:1: (lv_comp_6_0= ruleComparison )
                    {
                    // InternalSqlParser.g:4324:1: (lv_comp_6_0= ruleComparison )
                    // InternalSqlParser.g:4325:3: lv_comp_6_0= ruleComparison
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getExpressionAccess().getCompComparisonParserRuleCall_1_5_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_comp_6_0=ruleComparison();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getExpressionRule());
                      	        }
                             		set(
                             			current, 
                             			"comp",
                              		lv_comp_6_0, 
                              		"com.jaspersoft.studio.data.Sql.Comparison");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpression"


    // $ANTLR start "entryRuleIsNullValue"
    // InternalSqlParser.g:4349:1: entryRuleIsNullValue returns [String current=null] : iv_ruleIsNullValue= ruleIsNullValue EOF ;
    public final String entryRuleIsNullValue() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleIsNullValue = null;


        try {
            // InternalSqlParser.g:4350:1: (iv_ruleIsNullValue= ruleIsNullValue EOF )
            // InternalSqlParser.g:4351:2: iv_ruleIsNullValue= ruleIsNullValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getIsNullValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleIsNullValue=ruleIsNullValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleIsNullValue.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIsNullValue"


    // $ANTLR start "ruleIsNullValue"
    // InternalSqlParser.g:4358:1: ruleIsNullValue returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= IS (kw= NOT )? kw= NULL ) ;
    public final AntlrDatatypeRuleToken ruleIsNullValue() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // InternalSqlParser.g:4362:6: ( (kw= IS (kw= NOT )? kw= NULL ) )
            // InternalSqlParser.g:4363:1: (kw= IS (kw= NOT )? kw= NULL )
            {
            // InternalSqlParser.g:4363:1: (kw= IS (kw= NOT )? kw= NULL )
            // InternalSqlParser.g:4364:2: kw= IS (kw= NOT )? kw= NULL
            {
            kw=(Token)match(input,IS,FOLLOW_66); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current.merge(kw);
                      newLeafNode(kw, grammarAccess.getIsNullValueAccess().getISKeyword_0()); 
                  
            }
            // InternalSqlParser.g:4369:1: (kw= NOT )?
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==NOT) ) {
                alt86=1;
            }
            switch (alt86) {
                case 1 :
                    // InternalSqlParser.g:4370:2: kw= NOT
                    {
                    kw=(Token)match(input,NOT,FOLLOW_67); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getIsNullValueAccess().getNOTKeyword_1()); 
                          
                    }

                    }
                    break;

            }

            kw=(Token)match(input,NULL,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current.merge(kw);
                      newLeafNode(kw, grammarAccess.getIsNullValueAccess().getNULLKeyword_2()); 
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
                  
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIsNullValue"


    // $ANTLR start "entryRuleComparison"
    // InternalSqlParser.g:4389:1: entryRuleComparison returns [EObject current=null] : iv_ruleComparison= ruleComparison EOF ;
    public final EObject entryRuleComparison() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComparison = null;


        try {
            // InternalSqlParser.g:4390:2: (iv_ruleComparison= ruleComparison EOF )
            // InternalSqlParser.g:4391:2: iv_ruleComparison= ruleComparison EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getComparisonRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleComparison=ruleComparison();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleComparison; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleComparison"


    // $ANTLR start "ruleComparison"
    // InternalSqlParser.g:4398:1: ruleComparison returns [EObject current=null] : ( ( ( (lv_operator_0_1= GreaterThanSign | lv_operator_0_2= GreaterThanSignEqualsSign | lv_operator_0_3= LessThanSign | lv_operator_0_4= LessThanSignEqualsSign | lv_operator_0_5= EqualsSign | lv_operator_0_6= LessThanSignGreaterThanSign | lv_operator_0_7= ExclamationMarkEqualsSign | lv_operator_0_8= CircumflexAccentEqualsSign ) ) ) ( ( (lv_subOperator_1_1= ANY | lv_subOperator_1_2= ALL | lv_subOperator_1_3= SOME ) ) )? ( (lv_op2_2_0= ruleOperand ) ) ) ;
    public final EObject ruleComparison() throws RecognitionException {
        EObject current = null;

        Token lv_operator_0_1=null;
        Token lv_operator_0_2=null;
        Token lv_operator_0_3=null;
        Token lv_operator_0_4=null;
        Token lv_operator_0_5=null;
        Token lv_operator_0_6=null;
        Token lv_operator_0_7=null;
        Token lv_operator_0_8=null;
        Token lv_subOperator_1_1=null;
        Token lv_subOperator_1_2=null;
        Token lv_subOperator_1_3=null;
        EObject lv_op2_2_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:4401:28: ( ( ( ( (lv_operator_0_1= GreaterThanSign | lv_operator_0_2= GreaterThanSignEqualsSign | lv_operator_0_3= LessThanSign | lv_operator_0_4= LessThanSignEqualsSign | lv_operator_0_5= EqualsSign | lv_operator_0_6= LessThanSignGreaterThanSign | lv_operator_0_7= ExclamationMarkEqualsSign | lv_operator_0_8= CircumflexAccentEqualsSign ) ) ) ( ( (lv_subOperator_1_1= ANY | lv_subOperator_1_2= ALL | lv_subOperator_1_3= SOME ) ) )? ( (lv_op2_2_0= ruleOperand ) ) ) )
            // InternalSqlParser.g:4402:1: ( ( ( (lv_operator_0_1= GreaterThanSign | lv_operator_0_2= GreaterThanSignEqualsSign | lv_operator_0_3= LessThanSign | lv_operator_0_4= LessThanSignEqualsSign | lv_operator_0_5= EqualsSign | lv_operator_0_6= LessThanSignGreaterThanSign | lv_operator_0_7= ExclamationMarkEqualsSign | lv_operator_0_8= CircumflexAccentEqualsSign ) ) ) ( ( (lv_subOperator_1_1= ANY | lv_subOperator_1_2= ALL | lv_subOperator_1_3= SOME ) ) )? ( (lv_op2_2_0= ruleOperand ) ) )
            {
            // InternalSqlParser.g:4402:1: ( ( ( (lv_operator_0_1= GreaterThanSign | lv_operator_0_2= GreaterThanSignEqualsSign | lv_operator_0_3= LessThanSign | lv_operator_0_4= LessThanSignEqualsSign | lv_operator_0_5= EqualsSign | lv_operator_0_6= LessThanSignGreaterThanSign | lv_operator_0_7= ExclamationMarkEqualsSign | lv_operator_0_8= CircumflexAccentEqualsSign ) ) ) ( ( (lv_subOperator_1_1= ANY | lv_subOperator_1_2= ALL | lv_subOperator_1_3= SOME ) ) )? ( (lv_op2_2_0= ruleOperand ) ) )
            // InternalSqlParser.g:4402:2: ( ( (lv_operator_0_1= GreaterThanSign | lv_operator_0_2= GreaterThanSignEqualsSign | lv_operator_0_3= LessThanSign | lv_operator_0_4= LessThanSignEqualsSign | lv_operator_0_5= EqualsSign | lv_operator_0_6= LessThanSignGreaterThanSign | lv_operator_0_7= ExclamationMarkEqualsSign | lv_operator_0_8= CircumflexAccentEqualsSign ) ) ) ( ( (lv_subOperator_1_1= ANY | lv_subOperator_1_2= ALL | lv_subOperator_1_3= SOME ) ) )? ( (lv_op2_2_0= ruleOperand ) )
            {
            // InternalSqlParser.g:4402:2: ( ( (lv_operator_0_1= GreaterThanSign | lv_operator_0_2= GreaterThanSignEqualsSign | lv_operator_0_3= LessThanSign | lv_operator_0_4= LessThanSignEqualsSign | lv_operator_0_5= EqualsSign | lv_operator_0_6= LessThanSignGreaterThanSign | lv_operator_0_7= ExclamationMarkEqualsSign | lv_operator_0_8= CircumflexAccentEqualsSign ) ) )
            // InternalSqlParser.g:4403:1: ( (lv_operator_0_1= GreaterThanSign | lv_operator_0_2= GreaterThanSignEqualsSign | lv_operator_0_3= LessThanSign | lv_operator_0_4= LessThanSignEqualsSign | lv_operator_0_5= EqualsSign | lv_operator_0_6= LessThanSignGreaterThanSign | lv_operator_0_7= ExclamationMarkEqualsSign | lv_operator_0_8= CircumflexAccentEqualsSign ) )
            {
            // InternalSqlParser.g:4403:1: ( (lv_operator_0_1= GreaterThanSign | lv_operator_0_2= GreaterThanSignEqualsSign | lv_operator_0_3= LessThanSign | lv_operator_0_4= LessThanSignEqualsSign | lv_operator_0_5= EqualsSign | lv_operator_0_6= LessThanSignGreaterThanSign | lv_operator_0_7= ExclamationMarkEqualsSign | lv_operator_0_8= CircumflexAccentEqualsSign ) )
            // InternalSqlParser.g:4404:1: (lv_operator_0_1= GreaterThanSign | lv_operator_0_2= GreaterThanSignEqualsSign | lv_operator_0_3= LessThanSign | lv_operator_0_4= LessThanSignEqualsSign | lv_operator_0_5= EqualsSign | lv_operator_0_6= LessThanSignGreaterThanSign | lv_operator_0_7= ExclamationMarkEqualsSign | lv_operator_0_8= CircumflexAccentEqualsSign )
            {
            // InternalSqlParser.g:4404:1: (lv_operator_0_1= GreaterThanSign | lv_operator_0_2= GreaterThanSignEqualsSign | lv_operator_0_3= LessThanSign | lv_operator_0_4= LessThanSignEqualsSign | lv_operator_0_5= EqualsSign | lv_operator_0_6= LessThanSignGreaterThanSign | lv_operator_0_7= ExclamationMarkEqualsSign | lv_operator_0_8= CircumflexAccentEqualsSign )
            int alt87=8;
            switch ( input.LA(1) ) {
            case GreaterThanSign:
                {
                alt87=1;
                }
                break;
            case GreaterThanSignEqualsSign:
                {
                alt87=2;
                }
                break;
            case LessThanSign:
                {
                alt87=3;
                }
                break;
            case LessThanSignEqualsSign:
                {
                alt87=4;
                }
                break;
            case EqualsSign:
                {
                alt87=5;
                }
                break;
            case LessThanSignGreaterThanSign:
                {
                alt87=6;
                }
                break;
            case ExclamationMarkEqualsSign:
                {
                alt87=7;
                }
                break;
            case CircumflexAccentEqualsSign:
                {
                alt87=8;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 87, 0, input);

                throw nvae;
            }

            switch (alt87) {
                case 1 :
                    // InternalSqlParser.g:4405:3: lv_operator_0_1= GreaterThanSign
                    {
                    lv_operator_0_1=(Token)match(input,GreaterThanSign,FOLLOW_68); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_operator_0_1, grammarAccess.getComparisonAccess().getOperatorGreaterThanSignKeyword_0_0_0());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getComparisonRule());
                      	        }
                             		setWithLastConsumed(current, "operator", lv_operator_0_1, null);
                      	    
                    }

                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:4418:8: lv_operator_0_2= GreaterThanSignEqualsSign
                    {
                    lv_operator_0_2=(Token)match(input,GreaterThanSignEqualsSign,FOLLOW_68); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_operator_0_2, grammarAccess.getComparisonAccess().getOperatorGreaterThanSignEqualsSignKeyword_0_0_1());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getComparisonRule());
                      	        }
                             		setWithLastConsumed(current, "operator", lv_operator_0_2, null);
                      	    
                    }

                    }
                    break;
                case 3 :
                    // InternalSqlParser.g:4431:8: lv_operator_0_3= LessThanSign
                    {
                    lv_operator_0_3=(Token)match(input,LessThanSign,FOLLOW_68); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_operator_0_3, grammarAccess.getComparisonAccess().getOperatorLessThanSignKeyword_0_0_2());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getComparisonRule());
                      	        }
                             		setWithLastConsumed(current, "operator", lv_operator_0_3, null);
                      	    
                    }

                    }
                    break;
                case 4 :
                    // InternalSqlParser.g:4444:8: lv_operator_0_4= LessThanSignEqualsSign
                    {
                    lv_operator_0_4=(Token)match(input,LessThanSignEqualsSign,FOLLOW_68); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_operator_0_4, grammarAccess.getComparisonAccess().getOperatorLessThanSignEqualsSignKeyword_0_0_3());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getComparisonRule());
                      	        }
                             		setWithLastConsumed(current, "operator", lv_operator_0_4, null);
                      	    
                    }

                    }
                    break;
                case 5 :
                    // InternalSqlParser.g:4457:8: lv_operator_0_5= EqualsSign
                    {
                    lv_operator_0_5=(Token)match(input,EqualsSign,FOLLOW_68); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_operator_0_5, grammarAccess.getComparisonAccess().getOperatorEqualsSignKeyword_0_0_4());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getComparisonRule());
                      	        }
                             		setWithLastConsumed(current, "operator", lv_operator_0_5, null);
                      	    
                    }

                    }
                    break;
                case 6 :
                    // InternalSqlParser.g:4470:8: lv_operator_0_6= LessThanSignGreaterThanSign
                    {
                    lv_operator_0_6=(Token)match(input,LessThanSignGreaterThanSign,FOLLOW_68); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_operator_0_6, grammarAccess.getComparisonAccess().getOperatorLessThanSignGreaterThanSignKeyword_0_0_5());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getComparisonRule());
                      	        }
                             		setWithLastConsumed(current, "operator", lv_operator_0_6, null);
                      	    
                    }

                    }
                    break;
                case 7 :
                    // InternalSqlParser.g:4483:8: lv_operator_0_7= ExclamationMarkEqualsSign
                    {
                    lv_operator_0_7=(Token)match(input,ExclamationMarkEqualsSign,FOLLOW_68); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_operator_0_7, grammarAccess.getComparisonAccess().getOperatorExclamationMarkEqualsSignKeyword_0_0_6());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getComparisonRule());
                      	        }
                             		setWithLastConsumed(current, "operator", lv_operator_0_7, null);
                      	    
                    }

                    }
                    break;
                case 8 :
                    // InternalSqlParser.g:4496:8: lv_operator_0_8= CircumflexAccentEqualsSign
                    {
                    lv_operator_0_8=(Token)match(input,CircumflexAccentEqualsSign,FOLLOW_68); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_operator_0_8, grammarAccess.getComparisonAccess().getOperatorCircumflexAccentEqualsSignKeyword_0_0_7());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getComparisonRule());
                      	        }
                             		setWithLastConsumed(current, "operator", lv_operator_0_8, null);
                      	    
                    }

                    }
                    break;

            }


            }


            }

            // InternalSqlParser.g:4512:2: ( ( (lv_subOperator_1_1= ANY | lv_subOperator_1_2= ALL | lv_subOperator_1_3= SOME ) ) )?
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==SOME||LA89_0==ALL||LA89_0==ANY) ) {
                alt89=1;
            }
            switch (alt89) {
                case 1 :
                    // InternalSqlParser.g:4513:1: ( (lv_subOperator_1_1= ANY | lv_subOperator_1_2= ALL | lv_subOperator_1_3= SOME ) )
                    {
                    // InternalSqlParser.g:4513:1: ( (lv_subOperator_1_1= ANY | lv_subOperator_1_2= ALL | lv_subOperator_1_3= SOME ) )
                    // InternalSqlParser.g:4514:1: (lv_subOperator_1_1= ANY | lv_subOperator_1_2= ALL | lv_subOperator_1_3= SOME )
                    {
                    // InternalSqlParser.g:4514:1: (lv_subOperator_1_1= ANY | lv_subOperator_1_2= ALL | lv_subOperator_1_3= SOME )
                    int alt88=3;
                    switch ( input.LA(1) ) {
                    case ANY:
                        {
                        alt88=1;
                        }
                        break;
                    case ALL:
                        {
                        alt88=2;
                        }
                        break;
                    case SOME:
                        {
                        alt88=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 88, 0, input);

                        throw nvae;
                    }

                    switch (alt88) {
                        case 1 :
                            // InternalSqlParser.g:4515:3: lv_subOperator_1_1= ANY
                            {
                            lv_subOperator_1_1=(Token)match(input,ANY,FOLLOW_60); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                      newLeafNode(lv_subOperator_1_1, grammarAccess.getComparisonAccess().getSubOperatorANYKeyword_1_0_0());
                                  
                            }
                            if ( state.backtracking==0 ) {

                              	        if (current==null) {
                              	            current = createModelElement(grammarAccess.getComparisonRule());
                              	        }
                                     		setWithLastConsumed(current, "subOperator", lv_subOperator_1_1, null);
                              	    
                            }

                            }
                            break;
                        case 2 :
                            // InternalSqlParser.g:4528:8: lv_subOperator_1_2= ALL
                            {
                            lv_subOperator_1_2=(Token)match(input,ALL,FOLLOW_60); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                      newLeafNode(lv_subOperator_1_2, grammarAccess.getComparisonAccess().getSubOperatorALLKeyword_1_0_1());
                                  
                            }
                            if ( state.backtracking==0 ) {

                              	        if (current==null) {
                              	            current = createModelElement(grammarAccess.getComparisonRule());
                              	        }
                                     		setWithLastConsumed(current, "subOperator", lv_subOperator_1_2, null);
                              	    
                            }

                            }
                            break;
                        case 3 :
                            // InternalSqlParser.g:4541:8: lv_subOperator_1_3= SOME
                            {
                            lv_subOperator_1_3=(Token)match(input,SOME,FOLLOW_60); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                      newLeafNode(lv_subOperator_1_3, grammarAccess.getComparisonAccess().getSubOperatorSOMEKeyword_1_0_2());
                                  
                            }
                            if ( state.backtracking==0 ) {

                              	        if (current==null) {
                              	            current = createModelElement(grammarAccess.getComparisonRule());
                              	        }
                                     		setWithLastConsumed(current, "subOperator", lv_subOperator_1_3, null);
                              	    
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;

            }

            // InternalSqlParser.g:4557:3: ( (lv_op2_2_0= ruleOperand ) )
            // InternalSqlParser.g:4558:1: (lv_op2_2_0= ruleOperand )
            {
            // InternalSqlParser.g:4558:1: (lv_op2_2_0= ruleOperand )
            // InternalSqlParser.g:4559:3: lv_op2_2_0= ruleOperand
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getComparisonAccess().getOp2OperandParserRuleCall_2_0()); 
              	    
            }
            pushFollow(FOLLOW_2);
            lv_op2_2_0=ruleOperand();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getComparisonRule());
              	        }
                     		set(
                     			current, 
                     			"op2",
                      		lv_op2_2_0, 
                      		"com.jaspersoft.studio.data.Sql.Operand");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleComparison"


    // $ANTLR start "entryRuleLike"
    // InternalSqlParser.g:4583:1: entryRuleLike returns [EObject current=null] : iv_ruleLike= ruleLike EOF ;
    public final EObject entryRuleLike() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLike = null;


        try {
            // InternalSqlParser.g:4584:2: (iv_ruleLike= ruleLike EOF )
            // InternalSqlParser.g:4585:2: iv_ruleLike= ruleLike EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLikeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleLike=ruleLike();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleLike; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLike"


    // $ANTLR start "ruleLike"
    // InternalSqlParser.g:4592:1: ruleLike returns [EObject current=null] : ( ( (lv_opLike_0_0= ruleLikeValue ) ) ( (lv_op2_1_0= ruleLikeOperand ) ) ) ;
    public final EObject ruleLike() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_opLike_0_0 = null;

        EObject lv_op2_1_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:4595:28: ( ( ( (lv_opLike_0_0= ruleLikeValue ) ) ( (lv_op2_1_0= ruleLikeOperand ) ) ) )
            // InternalSqlParser.g:4596:1: ( ( (lv_opLike_0_0= ruleLikeValue ) ) ( (lv_op2_1_0= ruleLikeOperand ) ) )
            {
            // InternalSqlParser.g:4596:1: ( ( (lv_opLike_0_0= ruleLikeValue ) ) ( (lv_op2_1_0= ruleLikeOperand ) ) )
            // InternalSqlParser.g:4596:2: ( (lv_opLike_0_0= ruleLikeValue ) ) ( (lv_op2_1_0= ruleLikeOperand ) )
            {
            // InternalSqlParser.g:4596:2: ( (lv_opLike_0_0= ruleLikeValue ) )
            // InternalSqlParser.g:4597:1: (lv_opLike_0_0= ruleLikeValue )
            {
            // InternalSqlParser.g:4597:1: (lv_opLike_0_0= ruleLikeValue )
            // InternalSqlParser.g:4598:3: lv_opLike_0_0= ruleLikeValue
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getLikeAccess().getOpLikeLikeValueParserRuleCall_0_0()); 
              	    
            }
            pushFollow(FOLLOW_69);
            lv_opLike_0_0=ruleLikeValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getLikeRule());
              	        }
                     		set(
                     			current, 
                     			"opLike",
                      		lv_opLike_0_0, 
                      		"com.jaspersoft.studio.data.Sql.LikeValue");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:4614:2: ( (lv_op2_1_0= ruleLikeOperand ) )
            // InternalSqlParser.g:4615:1: (lv_op2_1_0= ruleLikeOperand )
            {
            // InternalSqlParser.g:4615:1: (lv_op2_1_0= ruleLikeOperand )
            // InternalSqlParser.g:4616:3: lv_op2_1_0= ruleLikeOperand
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getLikeAccess().getOp2LikeOperandParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_2);
            lv_op2_1_0=ruleLikeOperand();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getLikeRule());
              	        }
                     		set(
                     			current, 
                     			"op2",
                      		lv_op2_1_0, 
                      		"com.jaspersoft.studio.data.Sql.LikeOperand");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLike"


    // $ANTLR start "entryRuleLikeValue"
    // InternalSqlParser.g:4640:1: entryRuleLikeValue returns [String current=null] : iv_ruleLikeValue= ruleLikeValue EOF ;
    public final String entryRuleLikeValue() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleLikeValue = null;


        try {
            // InternalSqlParser.g:4641:1: (iv_ruleLikeValue= ruleLikeValue EOF )
            // InternalSqlParser.g:4642:2: iv_ruleLikeValue= ruleLikeValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLikeValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleLikeValue=ruleLikeValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleLikeValue.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLikeValue"


    // $ANTLR start "ruleLikeValue"
    // InternalSqlParser.g:4649:1: ruleLikeValue returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= NOT )? kw= LIKE ) ;
    public final AntlrDatatypeRuleToken ruleLikeValue() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // InternalSqlParser.g:4653:6: ( ( (kw= NOT )? kw= LIKE ) )
            // InternalSqlParser.g:4654:1: ( (kw= NOT )? kw= LIKE )
            {
            // InternalSqlParser.g:4654:1: ( (kw= NOT )? kw= LIKE )
            // InternalSqlParser.g:4654:2: (kw= NOT )? kw= LIKE
            {
            // InternalSqlParser.g:4654:2: (kw= NOT )?
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==NOT) ) {
                alt90=1;
            }
            switch (alt90) {
                case 1 :
                    // InternalSqlParser.g:4655:2: kw= NOT
                    {
                    kw=(Token)match(input,NOT,FOLLOW_70); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getLikeValueAccess().getNOTKeyword_0()); 
                          
                    }

                    }
                    break;

            }

            kw=(Token)match(input,LIKE,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current.merge(kw);
                      newLeafNode(kw, grammarAccess.getLikeValueAccess().getLIKEKeyword_1()); 
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
                  
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLikeValue"


    // $ANTLR start "entryRuleLikeOperand"
    // InternalSqlParser.g:4674:1: entryRuleLikeOperand returns [EObject current=null] : iv_ruleLikeOperand= ruleLikeOperand EOF ;
    public final EObject entryRuleLikeOperand() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLikeOperand = null;


        try {
            // InternalSqlParser.g:4675:2: (iv_ruleLikeOperand= ruleLikeOperand EOF )
            // InternalSqlParser.g:4676:2: iv_ruleLikeOperand= ruleLikeOperand EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLikeOperandRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleLikeOperand=ruleLikeOperand();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleLikeOperand; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLikeOperand"


    // $ANTLR start "ruleLikeOperand"
    // InternalSqlParser.g:4683:1: ruleLikeOperand returns [EObject current=null] : ( ( (lv_op2_0_0= ruleStringOperand ) ) | ( (lv_fop2_1_0= ruleOperandFunction ) ) | ( (lv_fcast_2_0= ruleOpFunctionCast ) ) | ( (lv_fparam_3_0= ruleParameterOperand ) ) ) ;
    public final EObject ruleLikeOperand() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_op2_0_0 = null;

        EObject lv_fop2_1_0 = null;

        EObject lv_fcast_2_0 = null;

        EObject lv_fparam_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:4686:28: ( ( ( (lv_op2_0_0= ruleStringOperand ) ) | ( (lv_fop2_1_0= ruleOperandFunction ) ) | ( (lv_fcast_2_0= ruleOpFunctionCast ) ) | ( (lv_fparam_3_0= ruleParameterOperand ) ) ) )
            // InternalSqlParser.g:4687:1: ( ( (lv_op2_0_0= ruleStringOperand ) ) | ( (lv_fop2_1_0= ruleOperandFunction ) ) | ( (lv_fcast_2_0= ruleOpFunctionCast ) ) | ( (lv_fparam_3_0= ruleParameterOperand ) ) )
            {
            // InternalSqlParser.g:4687:1: ( ( (lv_op2_0_0= ruleStringOperand ) ) | ( (lv_fop2_1_0= ruleOperandFunction ) ) | ( (lv_fcast_2_0= ruleOpFunctionCast ) ) | ( (lv_fparam_3_0= ruleParameterOperand ) ) )
            int alt91=4;
            switch ( input.LA(1) ) {
            case RULE_STRING_:
                {
                alt91=1;
                }
                break;
            case RULE_ID:
                {
                alt91=2;
                }
                break;
            case CAST:
                {
                alt91=3;
                }
                break;
            case RULE_JRPARAM:
                {
                alt91=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 91, 0, input);

                throw nvae;
            }

            switch (alt91) {
                case 1 :
                    // InternalSqlParser.g:4687:2: ( (lv_op2_0_0= ruleStringOperand ) )
                    {
                    // InternalSqlParser.g:4687:2: ( (lv_op2_0_0= ruleStringOperand ) )
                    // InternalSqlParser.g:4688:1: (lv_op2_0_0= ruleStringOperand )
                    {
                    // InternalSqlParser.g:4688:1: (lv_op2_0_0= ruleStringOperand )
                    // InternalSqlParser.g:4689:3: lv_op2_0_0= ruleStringOperand
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getLikeOperandAccess().getOp2StringOperandParserRuleCall_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_op2_0_0=ruleStringOperand();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getLikeOperandRule());
                      	        }
                             		set(
                             			current, 
                             			"op2",
                              		lv_op2_0_0, 
                              		"com.jaspersoft.studio.data.Sql.StringOperand");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:4706:6: ( (lv_fop2_1_0= ruleOperandFunction ) )
                    {
                    // InternalSqlParser.g:4706:6: ( (lv_fop2_1_0= ruleOperandFunction ) )
                    // InternalSqlParser.g:4707:1: (lv_fop2_1_0= ruleOperandFunction )
                    {
                    // InternalSqlParser.g:4707:1: (lv_fop2_1_0= ruleOperandFunction )
                    // InternalSqlParser.g:4708:3: lv_fop2_1_0= ruleOperandFunction
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getLikeOperandAccess().getFop2OperandFunctionParserRuleCall_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_fop2_1_0=ruleOperandFunction();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getLikeOperandRule());
                      	        }
                             		set(
                             			current, 
                             			"fop2",
                              		lv_fop2_1_0, 
                              		"com.jaspersoft.studio.data.Sql.OperandFunction");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSqlParser.g:4725:6: ( (lv_fcast_2_0= ruleOpFunctionCast ) )
                    {
                    // InternalSqlParser.g:4725:6: ( (lv_fcast_2_0= ruleOpFunctionCast ) )
                    // InternalSqlParser.g:4726:1: (lv_fcast_2_0= ruleOpFunctionCast )
                    {
                    // InternalSqlParser.g:4726:1: (lv_fcast_2_0= ruleOpFunctionCast )
                    // InternalSqlParser.g:4727:3: lv_fcast_2_0= ruleOpFunctionCast
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getLikeOperandAccess().getFcastOpFunctionCastParserRuleCall_2_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_fcast_2_0=ruleOpFunctionCast();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getLikeOperandRule());
                      	        }
                             		set(
                             			current, 
                             			"fcast",
                              		lv_fcast_2_0, 
                              		"com.jaspersoft.studio.data.Sql.OpFunctionCast");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalSqlParser.g:4744:6: ( (lv_fparam_3_0= ruleParameterOperand ) )
                    {
                    // InternalSqlParser.g:4744:6: ( (lv_fparam_3_0= ruleParameterOperand ) )
                    // InternalSqlParser.g:4745:1: (lv_fparam_3_0= ruleParameterOperand )
                    {
                    // InternalSqlParser.g:4745:1: (lv_fparam_3_0= ruleParameterOperand )
                    // InternalSqlParser.g:4746:3: lv_fparam_3_0= ruleParameterOperand
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getLikeOperandAccess().getFparamParameterOperandParserRuleCall_3_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_fparam_3_0=ruleParameterOperand();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getLikeOperandRule());
                      	        }
                             		set(
                             			current, 
                             			"fparam",
                              		lv_fparam_3_0, 
                              		"com.jaspersoft.studio.data.Sql.ParameterOperand");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLikeOperand"


    // $ANTLR start "entryRuleBetween"
    // InternalSqlParser.g:4770:1: entryRuleBetween returns [EObject current=null] : iv_ruleBetween= ruleBetween EOF ;
    public final EObject entryRuleBetween() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBetween = null;


        try {
            // InternalSqlParser.g:4771:2: (iv_ruleBetween= ruleBetween EOF )
            // InternalSqlParser.g:4772:2: iv_ruleBetween= ruleBetween EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBetweenRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleBetween=ruleBetween();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBetween; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBetween"


    // $ANTLR start "ruleBetween"
    // InternalSqlParser.g:4779:1: ruleBetween returns [EObject current=null] : ( ( (lv_opBetween_0_0= ruleBetweenValue ) ) ( (lv_op2_1_0= ruleOperandGroup ) ) otherlv_2= AND ( (lv_op3_3_0= ruleOperandGroup ) ) ) ;
    public final EObject ruleBetween() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_opBetween_0_0 = null;

        EObject lv_op2_1_0 = null;

        EObject lv_op3_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:4782:28: ( ( ( (lv_opBetween_0_0= ruleBetweenValue ) ) ( (lv_op2_1_0= ruleOperandGroup ) ) otherlv_2= AND ( (lv_op3_3_0= ruleOperandGroup ) ) ) )
            // InternalSqlParser.g:4783:1: ( ( (lv_opBetween_0_0= ruleBetweenValue ) ) ( (lv_op2_1_0= ruleOperandGroup ) ) otherlv_2= AND ( (lv_op3_3_0= ruleOperandGroup ) ) )
            {
            // InternalSqlParser.g:4783:1: ( ( (lv_opBetween_0_0= ruleBetweenValue ) ) ( (lv_op2_1_0= ruleOperandGroup ) ) otherlv_2= AND ( (lv_op3_3_0= ruleOperandGroup ) ) )
            // InternalSqlParser.g:4783:2: ( (lv_opBetween_0_0= ruleBetweenValue ) ) ( (lv_op2_1_0= ruleOperandGroup ) ) otherlv_2= AND ( (lv_op3_3_0= ruleOperandGroup ) )
            {
            // InternalSqlParser.g:4783:2: ( (lv_opBetween_0_0= ruleBetweenValue ) )
            // InternalSqlParser.g:4784:1: (lv_opBetween_0_0= ruleBetweenValue )
            {
            // InternalSqlParser.g:4784:1: (lv_opBetween_0_0= ruleBetweenValue )
            // InternalSqlParser.g:4785:3: lv_opBetween_0_0= ruleBetweenValue
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getBetweenAccess().getOpBetweenBetweenValueParserRuleCall_0_0()); 
              	    
            }
            pushFollow(FOLLOW_60);
            lv_opBetween_0_0=ruleBetweenValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getBetweenRule());
              	        }
                     		set(
                     			current, 
                     			"opBetween",
                      		lv_opBetween_0_0, 
                      		"com.jaspersoft.studio.data.Sql.BetweenValue");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:4801:2: ( (lv_op2_1_0= ruleOperandGroup ) )
            // InternalSqlParser.g:4802:1: (lv_op2_1_0= ruleOperandGroup )
            {
            // InternalSqlParser.g:4802:1: (lv_op2_1_0= ruleOperandGroup )
            // InternalSqlParser.g:4803:3: lv_op2_1_0= ruleOperandGroup
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getBetweenAccess().getOp2OperandGroupParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_71);
            lv_op2_1_0=ruleOperandGroup();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getBetweenRule());
              	        }
                     		set(
                     			current, 
                     			"op2",
                      		lv_op2_1_0, 
                      		"com.jaspersoft.studio.data.Sql.OperandGroup");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_2=(Token)match(input,AND,FOLLOW_60); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getBetweenAccess().getANDKeyword_2());
                  
            }
            // InternalSqlParser.g:4824:1: ( (lv_op3_3_0= ruleOperandGroup ) )
            // InternalSqlParser.g:4825:1: (lv_op3_3_0= ruleOperandGroup )
            {
            // InternalSqlParser.g:4825:1: (lv_op3_3_0= ruleOperandGroup )
            // InternalSqlParser.g:4826:3: lv_op3_3_0= ruleOperandGroup
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getBetweenAccess().getOp3OperandGroupParserRuleCall_3_0()); 
              	    
            }
            pushFollow(FOLLOW_2);
            lv_op3_3_0=ruleOperandGroup();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getBetweenRule());
              	        }
                     		set(
                     			current, 
                     			"op3",
                      		lv_op3_3_0, 
                      		"com.jaspersoft.studio.data.Sql.OperandGroup");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBetween"


    // $ANTLR start "entryRuleBetweenValue"
    // InternalSqlParser.g:4850:1: entryRuleBetweenValue returns [String current=null] : iv_ruleBetweenValue= ruleBetweenValue EOF ;
    public final String entryRuleBetweenValue() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBetweenValue = null;


        try {
            // InternalSqlParser.g:4851:1: (iv_ruleBetweenValue= ruleBetweenValue EOF )
            // InternalSqlParser.g:4852:2: iv_ruleBetweenValue= ruleBetweenValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBetweenValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleBetweenValue=ruleBetweenValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBetweenValue.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBetweenValue"


    // $ANTLR start "ruleBetweenValue"
    // InternalSqlParser.g:4859:1: ruleBetweenValue returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= NOT )? kw= BETWEEN ) ;
    public final AntlrDatatypeRuleToken ruleBetweenValue() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // InternalSqlParser.g:4863:6: ( ( (kw= NOT )? kw= BETWEEN ) )
            // InternalSqlParser.g:4864:1: ( (kw= NOT )? kw= BETWEEN )
            {
            // InternalSqlParser.g:4864:1: ( (kw= NOT )? kw= BETWEEN )
            // InternalSqlParser.g:4864:2: (kw= NOT )? kw= BETWEEN
            {
            // InternalSqlParser.g:4864:2: (kw= NOT )?
            int alt92=2;
            int LA92_0 = input.LA(1);

            if ( (LA92_0==NOT) ) {
                alt92=1;
            }
            switch (alt92) {
                case 1 :
                    // InternalSqlParser.g:4865:2: kw= NOT
                    {
                    kw=(Token)match(input,NOT,FOLLOW_72); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getBetweenValueAccess().getNOTKeyword_0()); 
                          
                    }

                    }
                    break;

            }

            kw=(Token)match(input,BETWEEN,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current.merge(kw);
                      newLeafNode(kw, grammarAccess.getBetweenValueAccess().getBETWEENKeyword_1()); 
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
                  
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBetweenValue"


    // $ANTLR start "entryRuleInOperator"
    // InternalSqlParser.g:4884:1: entryRuleInOperator returns [EObject current=null] : iv_ruleInOperator= ruleInOperator EOF ;
    public final EObject entryRuleInOperator() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInOperator = null;


        try {
            // InternalSqlParser.g:4885:2: (iv_ruleInOperator= ruleInOperator EOF )
            // InternalSqlParser.g:4886:2: iv_ruleInOperator= ruleInOperator EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getInOperatorRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleInOperator=ruleInOperator();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleInOperator; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInOperator"


    // $ANTLR start "ruleInOperator"
    // InternalSqlParser.g:4893:1: ruleInOperator returns [EObject current=null] : ( () ( (lv_op_1_0= ruleInValue ) ) ( ( (lv_subquery_2_0= ruleSubQueryOperand ) ) | ( (lv_opList_3_0= ruleOperandListGroup ) ) ) ) ;
    public final EObject ruleInOperator() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_op_1_0 = null;

        EObject lv_subquery_2_0 = null;

        EObject lv_opList_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:4896:28: ( ( () ( (lv_op_1_0= ruleInValue ) ) ( ( (lv_subquery_2_0= ruleSubQueryOperand ) ) | ( (lv_opList_3_0= ruleOperandListGroup ) ) ) ) )
            // InternalSqlParser.g:4897:1: ( () ( (lv_op_1_0= ruleInValue ) ) ( ( (lv_subquery_2_0= ruleSubQueryOperand ) ) | ( (lv_opList_3_0= ruleOperandListGroup ) ) ) )
            {
            // InternalSqlParser.g:4897:1: ( () ( (lv_op_1_0= ruleInValue ) ) ( ( (lv_subquery_2_0= ruleSubQueryOperand ) ) | ( (lv_opList_3_0= ruleOperandListGroup ) ) ) )
            // InternalSqlParser.g:4897:2: () ( (lv_op_1_0= ruleInValue ) ) ( ( (lv_subquery_2_0= ruleSubQueryOperand ) ) | ( (lv_opList_3_0= ruleOperandListGroup ) ) )
            {
            // InternalSqlParser.g:4897:2: ()
            // InternalSqlParser.g:4898:2: 
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getInOperatorAccess().getInOperAction_0(),
                          current);
                  
            }

            }

            // InternalSqlParser.g:4906:2: ( (lv_op_1_0= ruleInValue ) )
            // InternalSqlParser.g:4907:1: (lv_op_1_0= ruleInValue )
            {
            // InternalSqlParser.g:4907:1: (lv_op_1_0= ruleInValue )
            // InternalSqlParser.g:4908:3: lv_op_1_0= ruleInValue
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getInOperatorAccess().getOpInValueParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_7);
            lv_op_1_0=ruleInValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getInOperatorRule());
              	        }
                     		set(
                     			current, 
                     			"op",
                      		lv_op_1_0, 
                      		"com.jaspersoft.studio.data.Sql.InValue");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:4924:2: ( ( (lv_subquery_2_0= ruleSubQueryOperand ) ) | ( (lv_opList_3_0= ruleOperandListGroup ) ) )
            int alt93=2;
            int LA93_0 = input.LA(1);

            if ( (LA93_0==LeftParenthesis) ) {
                int LA93_1 = input.LA(2);

                if ( (LA93_1==SELECT) ) {
                    alt93=1;
                }
                else if ( ((LA93_1>=RULE_SIGNED_DOUBLE && LA93_1<=RULE_TIMESTAMP)||LA93_1==RULE_STRING_) ) {
                    alt93=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 93, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 93, 0, input);

                throw nvae;
            }
            switch (alt93) {
                case 1 :
                    // InternalSqlParser.g:4924:3: ( (lv_subquery_2_0= ruleSubQueryOperand ) )
                    {
                    // InternalSqlParser.g:4924:3: ( (lv_subquery_2_0= ruleSubQueryOperand ) )
                    // InternalSqlParser.g:4925:1: (lv_subquery_2_0= ruleSubQueryOperand )
                    {
                    // InternalSqlParser.g:4925:1: (lv_subquery_2_0= ruleSubQueryOperand )
                    // InternalSqlParser.g:4926:3: lv_subquery_2_0= ruleSubQueryOperand
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getInOperatorAccess().getSubquerySubQueryOperandParserRuleCall_2_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_subquery_2_0=ruleSubQueryOperand();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getInOperatorRule());
                      	        }
                             		set(
                             			current, 
                             			"subquery",
                              		lv_subquery_2_0, 
                              		"com.jaspersoft.studio.data.Sql.SubQueryOperand");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:4943:6: ( (lv_opList_3_0= ruleOperandListGroup ) )
                    {
                    // InternalSqlParser.g:4943:6: ( (lv_opList_3_0= ruleOperandListGroup ) )
                    // InternalSqlParser.g:4944:1: (lv_opList_3_0= ruleOperandListGroup )
                    {
                    // InternalSqlParser.g:4944:1: (lv_opList_3_0= ruleOperandListGroup )
                    // InternalSqlParser.g:4945:3: lv_opList_3_0= ruleOperandListGroup
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getInOperatorAccess().getOpListOperandListGroupParserRuleCall_2_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_opList_3_0=ruleOperandListGroup();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getInOperatorRule());
                      	        }
                             		set(
                             			current, 
                             			"opList",
                              		lv_opList_3_0, 
                              		"com.jaspersoft.studio.data.Sql.OperandListGroup");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInOperator"


    // $ANTLR start "entryRuleInValue"
    // InternalSqlParser.g:4969:1: entryRuleInValue returns [String current=null] : iv_ruleInValue= ruleInValue EOF ;
    public final String entryRuleInValue() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleInValue = null;


        try {
            // InternalSqlParser.g:4970:1: (iv_ruleInValue= ruleInValue EOF )
            // InternalSqlParser.g:4971:2: iv_ruleInValue= ruleInValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getInValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleInValue=ruleInValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleInValue.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInValue"


    // $ANTLR start "ruleInValue"
    // InternalSqlParser.g:4978:1: ruleInValue returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= NOT )? kw= IN ) ;
    public final AntlrDatatypeRuleToken ruleInValue() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // InternalSqlParser.g:4982:6: ( ( (kw= NOT )? kw= IN ) )
            // InternalSqlParser.g:4983:1: ( (kw= NOT )? kw= IN )
            {
            // InternalSqlParser.g:4983:1: ( (kw= NOT )? kw= IN )
            // InternalSqlParser.g:4983:2: (kw= NOT )? kw= IN
            {
            // InternalSqlParser.g:4983:2: (kw= NOT )?
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( (LA94_0==NOT) ) {
                alt94=1;
            }
            switch (alt94) {
                case 1 :
                    // InternalSqlParser.g:4984:2: kw= NOT
                    {
                    kw=(Token)match(input,NOT,FOLLOW_48); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getInValueAccess().getNOTKeyword_0()); 
                          
                    }

                    }
                    break;

            }

            kw=(Token)match(input,IN,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current.merge(kw);
                      newLeafNode(kw, grammarAccess.getInValueAccess().getINKeyword_1()); 
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
                  
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInValue"


    // $ANTLR start "entryRuleExistsOperator"
    // InternalSqlParser.g:5003:1: entryRuleExistsOperator returns [EObject current=null] : iv_ruleExistsOperator= ruleExistsOperator EOF ;
    public final EObject entryRuleExistsOperator() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExistsOperator = null;


        try {
            // InternalSqlParser.g:5004:2: (iv_ruleExistsOperator= ruleExistsOperator EOF )
            // InternalSqlParser.g:5005:2: iv_ruleExistsOperator= ruleExistsOperator EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExistsOperatorRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleExistsOperator=ruleExistsOperator();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleExistsOperator; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExistsOperator"


    // $ANTLR start "ruleExistsOperator"
    // InternalSqlParser.g:5012:1: ruleExistsOperator returns [EObject current=null] : ( () ( (lv_op_1_0= ruleExistsValue ) ) ( ( (lv_subquery_2_0= ruleSubQueryOperand ) ) | ( (lv_opList_3_0= ruleOperandListGroup ) ) ) ) ;
    public final EObject ruleExistsOperator() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_op_1_0 = null;

        EObject lv_subquery_2_0 = null;

        EObject lv_opList_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:5015:28: ( ( () ( (lv_op_1_0= ruleExistsValue ) ) ( ( (lv_subquery_2_0= ruleSubQueryOperand ) ) | ( (lv_opList_3_0= ruleOperandListGroup ) ) ) ) )
            // InternalSqlParser.g:5016:1: ( () ( (lv_op_1_0= ruleExistsValue ) ) ( ( (lv_subquery_2_0= ruleSubQueryOperand ) ) | ( (lv_opList_3_0= ruleOperandListGroup ) ) ) )
            {
            // InternalSqlParser.g:5016:1: ( () ( (lv_op_1_0= ruleExistsValue ) ) ( ( (lv_subquery_2_0= ruleSubQueryOperand ) ) | ( (lv_opList_3_0= ruleOperandListGroup ) ) ) )
            // InternalSqlParser.g:5016:2: () ( (lv_op_1_0= ruleExistsValue ) ) ( ( (lv_subquery_2_0= ruleSubQueryOperand ) ) | ( (lv_opList_3_0= ruleOperandListGroup ) ) )
            {
            // InternalSqlParser.g:5016:2: ()
            // InternalSqlParser.g:5017:2: 
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getExistsOperatorAccess().getExistsOperAction_0(),
                          current);
                  
            }

            }

            // InternalSqlParser.g:5025:2: ( (lv_op_1_0= ruleExistsValue ) )
            // InternalSqlParser.g:5026:1: (lv_op_1_0= ruleExistsValue )
            {
            // InternalSqlParser.g:5026:1: (lv_op_1_0= ruleExistsValue )
            // InternalSqlParser.g:5027:3: lv_op_1_0= ruleExistsValue
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getExistsOperatorAccess().getOpExistsValueParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_7);
            lv_op_1_0=ruleExistsValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getExistsOperatorRule());
              	        }
                     		set(
                     			current, 
                     			"op",
                      		lv_op_1_0, 
                      		"com.jaspersoft.studio.data.Sql.ExistsValue");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:5043:2: ( ( (lv_subquery_2_0= ruleSubQueryOperand ) ) | ( (lv_opList_3_0= ruleOperandListGroup ) ) )
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==LeftParenthesis) ) {
                int LA95_1 = input.LA(2);

                if ( (LA95_1==SELECT) ) {
                    alt95=1;
                }
                else if ( ((LA95_1>=RULE_SIGNED_DOUBLE && LA95_1<=RULE_TIMESTAMP)||LA95_1==RULE_STRING_) ) {
                    alt95=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 95, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 95, 0, input);

                throw nvae;
            }
            switch (alt95) {
                case 1 :
                    // InternalSqlParser.g:5043:3: ( (lv_subquery_2_0= ruleSubQueryOperand ) )
                    {
                    // InternalSqlParser.g:5043:3: ( (lv_subquery_2_0= ruleSubQueryOperand ) )
                    // InternalSqlParser.g:5044:1: (lv_subquery_2_0= ruleSubQueryOperand )
                    {
                    // InternalSqlParser.g:5044:1: (lv_subquery_2_0= ruleSubQueryOperand )
                    // InternalSqlParser.g:5045:3: lv_subquery_2_0= ruleSubQueryOperand
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getExistsOperatorAccess().getSubquerySubQueryOperandParserRuleCall_2_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_subquery_2_0=ruleSubQueryOperand();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getExistsOperatorRule());
                      	        }
                             		set(
                             			current, 
                             			"subquery",
                              		lv_subquery_2_0, 
                              		"com.jaspersoft.studio.data.Sql.SubQueryOperand");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:5062:6: ( (lv_opList_3_0= ruleOperandListGroup ) )
                    {
                    // InternalSqlParser.g:5062:6: ( (lv_opList_3_0= ruleOperandListGroup ) )
                    // InternalSqlParser.g:5063:1: (lv_opList_3_0= ruleOperandListGroup )
                    {
                    // InternalSqlParser.g:5063:1: (lv_opList_3_0= ruleOperandListGroup )
                    // InternalSqlParser.g:5064:3: lv_opList_3_0= ruleOperandListGroup
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getExistsOperatorAccess().getOpListOperandListGroupParserRuleCall_2_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_opList_3_0=ruleOperandListGroup();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getExistsOperatorRule());
                      	        }
                             		set(
                             			current, 
                             			"opList",
                              		lv_opList_3_0, 
                              		"com.jaspersoft.studio.data.Sql.OperandListGroup");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExistsOperator"


    // $ANTLR start "entryRuleExistsValue"
    // InternalSqlParser.g:5088:1: entryRuleExistsValue returns [String current=null] : iv_ruleExistsValue= ruleExistsValue EOF ;
    public final String entryRuleExistsValue() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleExistsValue = null;


        try {
            // InternalSqlParser.g:5089:1: (iv_ruleExistsValue= ruleExistsValue EOF )
            // InternalSqlParser.g:5090:2: iv_ruleExistsValue= ruleExistsValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExistsValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleExistsValue=ruleExistsValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleExistsValue.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExistsValue"


    // $ANTLR start "ruleExistsValue"
    // InternalSqlParser.g:5097:1: ruleExistsValue returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= NOT )? kw= EXISTS ) ;
    public final AntlrDatatypeRuleToken ruleExistsValue() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // InternalSqlParser.g:5101:6: ( ( (kw= NOT )? kw= EXISTS ) )
            // InternalSqlParser.g:5102:1: ( (kw= NOT )? kw= EXISTS )
            {
            // InternalSqlParser.g:5102:1: ( (kw= NOT )? kw= EXISTS )
            // InternalSqlParser.g:5102:2: (kw= NOT )? kw= EXISTS
            {
            // InternalSqlParser.g:5102:2: (kw= NOT )?
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==NOT) ) {
                alt96=1;
            }
            switch (alt96) {
                case 1 :
                    // InternalSqlParser.g:5103:2: kw= NOT
                    {
                    kw=(Token)match(input,NOT,FOLLOW_73); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getExistsValueAccess().getNOTKeyword_0()); 
                          
                    }

                    }
                    break;

            }

            kw=(Token)match(input,EXISTS,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current.merge(kw);
                      newLeafNode(kw, grammarAccess.getExistsValueAccess().getEXISTSKeyword_1()); 
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
                  
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExistsValue"


    // $ANTLR start "entryRuleOperandListGroup"
    // InternalSqlParser.g:5122:1: entryRuleOperandListGroup returns [EObject current=null] : iv_ruleOperandListGroup= ruleOperandListGroup EOF ;
    public final EObject entryRuleOperandListGroup() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOperandListGroup = null;


        try {
            // InternalSqlParser.g:5123:2: (iv_ruleOperandListGroup= ruleOperandListGroup EOF )
            // InternalSqlParser.g:5124:2: iv_ruleOperandListGroup= ruleOperandListGroup EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOperandListGroupRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOperandListGroup=ruleOperandListGroup();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOperandListGroup; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOperandListGroup"


    // $ANTLR start "ruleOperandListGroup"
    // InternalSqlParser.g:5131:1: ruleOperandListGroup returns [EObject current=null] : (otherlv_0= LeftParenthesis ( (lv_opGroup_1_0= ruleOperandList ) ) otherlv_2= RightParenthesis ) ;
    public final EObject ruleOperandListGroup() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_opGroup_1_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:5134:28: ( (otherlv_0= LeftParenthesis ( (lv_opGroup_1_0= ruleOperandList ) ) otherlv_2= RightParenthesis ) )
            // InternalSqlParser.g:5135:1: (otherlv_0= LeftParenthesis ( (lv_opGroup_1_0= ruleOperandList ) ) otherlv_2= RightParenthesis )
            {
            // InternalSqlParser.g:5135:1: (otherlv_0= LeftParenthesis ( (lv_opGroup_1_0= ruleOperandList ) ) otherlv_2= RightParenthesis )
            // InternalSqlParser.g:5136:2: otherlv_0= LeftParenthesis ( (lv_opGroup_1_0= ruleOperandList ) ) otherlv_2= RightParenthesis
            {
            otherlv_0=(Token)match(input,LeftParenthesis,FOLLOW_74); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getOperandListGroupAccess().getLeftParenthesisKeyword_0());
                  
            }
            // InternalSqlParser.g:5140:1: ( (lv_opGroup_1_0= ruleOperandList ) )
            // InternalSqlParser.g:5141:1: (lv_opGroup_1_0= ruleOperandList )
            {
            // InternalSqlParser.g:5141:1: (lv_opGroup_1_0= ruleOperandList )
            // InternalSqlParser.g:5142:3: lv_opGroup_1_0= ruleOperandList
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getOperandListGroupAccess().getOpGroupOperandListParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_8);
            lv_opGroup_1_0=ruleOperandList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getOperandListGroupRule());
              	        }
                     		set(
                     			current, 
                     			"opGroup",
                      		lv_opGroup_1_0, 
                      		"com.jaspersoft.studio.data.Sql.OperandList");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_2=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getOperandListGroupAccess().getRightParenthesisKeyword_2());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOperandListGroup"


    // $ANTLR start "entryRuleOperandList"
    // InternalSqlParser.g:5171:1: entryRuleOperandList returns [EObject current=null] : iv_ruleOperandList= ruleOperandList EOF ;
    public final EObject entryRuleOperandList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOperandList = null;


        try {
            // InternalSqlParser.g:5172:2: (iv_ruleOperandList= ruleOperandList EOF )
            // InternalSqlParser.g:5173:2: iv_ruleOperandList= ruleOperandList EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOperandListRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOperandList=ruleOperandList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOperandList; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOperandList"


    // $ANTLR start "ruleOperandList"
    // InternalSqlParser.g:5180:1: ruleOperandList returns [EObject current=null] : (this_ScalarOperand_0= ruleScalarOperand ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleScalarOperand ) ) )+ )? ) ;
    public final EObject ruleOperandList() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_ScalarOperand_0 = null;

        EObject lv_entries_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:5183:28: ( (this_ScalarOperand_0= ruleScalarOperand ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleScalarOperand ) ) )+ )? ) )
            // InternalSqlParser.g:5184:1: (this_ScalarOperand_0= ruleScalarOperand ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleScalarOperand ) ) )+ )? )
            {
            // InternalSqlParser.g:5184:1: (this_ScalarOperand_0= ruleScalarOperand ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleScalarOperand ) ) )+ )? )
            // InternalSqlParser.g:5185:2: this_ScalarOperand_0= ruleScalarOperand ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleScalarOperand ) ) )+ )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getOperandListAccess().getScalarOperandParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_9);
            this_ScalarOperand_0=ruleScalarOperand();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_ScalarOperand_0;
                      afterParserOrEnumRuleCall();
                  
            }
            // InternalSqlParser.g:5196:1: ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleScalarOperand ) ) )+ )?
            int alt98=2;
            int LA98_0 = input.LA(1);

            if ( (LA98_0==Comma) ) {
                alt98=1;
            }
            switch (alt98) {
                case 1 :
                    // InternalSqlParser.g:5196:2: () (otherlv_2= Comma ( (lv_entries_3_0= ruleScalarOperand ) ) )+
                    {
                    // InternalSqlParser.g:5196:2: ()
                    // InternalSqlParser.g:5197:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getOperandListAccess().getOpListEntriesAction_1_0(),
                                  current);
                          
                    }

                    }

                    // InternalSqlParser.g:5205:2: (otherlv_2= Comma ( (lv_entries_3_0= ruleScalarOperand ) ) )+
                    int cnt97=0;
                    loop97:
                    do {
                        int alt97=2;
                        int LA97_0 = input.LA(1);

                        if ( (LA97_0==Comma) ) {
                            alt97=1;
                        }


                        switch (alt97) {
                    	case 1 :
                    	    // InternalSqlParser.g:5206:2: otherlv_2= Comma ( (lv_entries_3_0= ruleScalarOperand ) )
                    	    {
                    	    otherlv_2=(Token)match(input,Comma,FOLLOW_74); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_2, grammarAccess.getOperandListAccess().getCommaKeyword_1_1_0());
                    	          
                    	    }
                    	    // InternalSqlParser.g:5210:1: ( (lv_entries_3_0= ruleScalarOperand ) )
                    	    // InternalSqlParser.g:5211:1: (lv_entries_3_0= ruleScalarOperand )
                    	    {
                    	    // InternalSqlParser.g:5211:1: (lv_entries_3_0= ruleScalarOperand )
                    	    // InternalSqlParser.g:5212:3: lv_entries_3_0= ruleScalarOperand
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getOperandListAccess().getEntriesScalarOperandParserRuleCall_1_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_9);
                    	    lv_entries_3_0=ruleScalarOperand();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getOperandListRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"entries",
                    	              		lv_entries_3_0, 
                    	              		"com.jaspersoft.studio.data.Sql.ScalarOperand");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt97 >= 1 ) break loop97;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(97, input);
                                throw eee;
                        }
                        cnt97++;
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOperandList"


    // $ANTLR start "entryRuleOperandGroup"
    // InternalSqlParser.g:5236:1: entryRuleOperandGroup returns [EObject current=null] : iv_ruleOperandGroup= ruleOperandGroup EOF ;
    public final EObject entryRuleOperandGroup() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOperandGroup = null;


        try {
            // InternalSqlParser.g:5237:2: (iv_ruleOperandGroup= ruleOperandGroup EOF )
            // InternalSqlParser.g:5238:2: iv_ruleOperandGroup= ruleOperandGroup EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOperandGroupRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOperandGroup=ruleOperandGroup();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOperandGroup; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOperandGroup"


    // $ANTLR start "ruleOperandGroup"
    // InternalSqlParser.g:5245:1: ruleOperandGroup returns [EObject current=null] : (this_Operand_0= ruleOperand | (otherlv_1= LeftParenthesis this_Operand_2= ruleOperand otherlv_3= RightParenthesis ) ) ;
    public final EObject ruleOperandGroup() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject this_Operand_0 = null;

        EObject this_Operand_2 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:5248:28: ( (this_Operand_0= ruleOperand | (otherlv_1= LeftParenthesis this_Operand_2= ruleOperand otherlv_3= RightParenthesis ) ) )
            // InternalSqlParser.g:5249:1: (this_Operand_0= ruleOperand | (otherlv_1= LeftParenthesis this_Operand_2= ruleOperand otherlv_3= RightParenthesis ) )
            {
            // InternalSqlParser.g:5249:1: (this_Operand_0= ruleOperand | (otherlv_1= LeftParenthesis this_Operand_2= ruleOperand otherlv_3= RightParenthesis ) )
            int alt99=2;
            int LA99_0 = input.LA(1);

            if ( (LA99_0==EXTRACT||LA99_0==CAST||LA99_0==CASE||(LA99_0>=RULE_JRPARAM && LA99_0<=RULE_JRNPARAM)||(LA99_0>=RULE_UNSIGNED && LA99_0<=RULE_SIGNED_DOUBLE)||(LA99_0>=RULE_STRING_ && LA99_0<=RULE_ID)) ) {
                alt99=1;
            }
            else if ( (LA99_0==LeftParenthesis) ) {
                int LA99_2 = input.LA(2);

                if ( (LA99_2==SELECT) ) {
                    alt99=1;
                }
                else if ( (LA99_2==EXTRACT||LA99_2==CAST||LA99_2==CASE||LA99_2==LeftParenthesis||(LA99_2>=RULE_JRPARAM && LA99_2<=RULE_JRNPARAM)||(LA99_2>=RULE_UNSIGNED && LA99_2<=RULE_SIGNED_DOUBLE)||(LA99_2>=RULE_STRING_ && LA99_2<=RULE_ID)) ) {
                    alt99=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 99, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 99, 0, input);

                throw nvae;
            }
            switch (alt99) {
                case 1 :
                    // InternalSqlParser.g:5250:2: this_Operand_0= ruleOperand
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getOperandGroupAccess().getOperandParserRuleCall_0()); 
                          
                    }
                    pushFollow(FOLLOW_2);
                    this_Operand_0=ruleOperand();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_Operand_0;
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:5262:6: (otherlv_1= LeftParenthesis this_Operand_2= ruleOperand otherlv_3= RightParenthesis )
                    {
                    // InternalSqlParser.g:5262:6: (otherlv_1= LeftParenthesis this_Operand_2= ruleOperand otherlv_3= RightParenthesis )
                    // InternalSqlParser.g:5263:2: otherlv_1= LeftParenthesis this_Operand_2= ruleOperand otherlv_3= RightParenthesis
                    {
                    otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_60); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_1, grammarAccess.getOperandGroupAccess().getLeftParenthesisKeyword_1_0());
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getOperandGroupAccess().getOperandParserRuleCall_1_1()); 
                          
                    }
                    pushFollow(FOLLOW_8);
                    this_Operand_2=ruleOperand();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_Operand_2;
                              afterParserOrEnumRuleCall();
                          
                    }
                    otherlv_3=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_3, grammarAccess.getOperandGroupAccess().getRightParenthesisKeyword_1_2());
                          
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOperandGroup"


    // $ANTLR start "entryRuleOperand"
    // InternalSqlParser.g:5292:1: entryRuleOperand returns [EObject current=null] : iv_ruleOperand= ruleOperand EOF ;
    public final EObject entryRuleOperand() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOperand = null;


        try {
            // InternalSqlParser.g:5293:2: (iv_ruleOperand= ruleOperand EOF )
            // InternalSqlParser.g:5294:2: iv_ruleOperand= ruleOperand EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOperandRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOperand=ruleOperand();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOperand; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOperand"


    // $ANTLR start "ruleOperand"
    // InternalSqlParser.g:5301:1: ruleOperand returns [EObject current=null] : ( ( (lv_op1_0_0= ruleOperandFragment ) ) ( ( ( () otherlv_2= PlusSign ) | ( () otherlv_4= HyphenMinus ) | ( () otherlv_6= VerticalLineVerticalLine ) | ( () this_STAR_8= RULE_STAR ) | ( () otherlv_10= Solidus ) ) ( (lv_right_11_0= ruleOperandFragment ) ) )* ) ;
    public final EObject ruleOperand() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token this_STAR_8=null;
        Token otherlv_10=null;
        EObject lv_op1_0_0 = null;

        EObject lv_right_11_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:5304:28: ( ( ( (lv_op1_0_0= ruleOperandFragment ) ) ( ( ( () otherlv_2= PlusSign ) | ( () otherlv_4= HyphenMinus ) | ( () otherlv_6= VerticalLineVerticalLine ) | ( () this_STAR_8= RULE_STAR ) | ( () otherlv_10= Solidus ) ) ( (lv_right_11_0= ruleOperandFragment ) ) )* ) )
            // InternalSqlParser.g:5305:1: ( ( (lv_op1_0_0= ruleOperandFragment ) ) ( ( ( () otherlv_2= PlusSign ) | ( () otherlv_4= HyphenMinus ) | ( () otherlv_6= VerticalLineVerticalLine ) | ( () this_STAR_8= RULE_STAR ) | ( () otherlv_10= Solidus ) ) ( (lv_right_11_0= ruleOperandFragment ) ) )* )
            {
            // InternalSqlParser.g:5305:1: ( ( (lv_op1_0_0= ruleOperandFragment ) ) ( ( ( () otherlv_2= PlusSign ) | ( () otherlv_4= HyphenMinus ) | ( () otherlv_6= VerticalLineVerticalLine ) | ( () this_STAR_8= RULE_STAR ) | ( () otherlv_10= Solidus ) ) ( (lv_right_11_0= ruleOperandFragment ) ) )* )
            // InternalSqlParser.g:5305:2: ( (lv_op1_0_0= ruleOperandFragment ) ) ( ( ( () otherlv_2= PlusSign ) | ( () otherlv_4= HyphenMinus ) | ( () otherlv_6= VerticalLineVerticalLine ) | ( () this_STAR_8= RULE_STAR ) | ( () otherlv_10= Solidus ) ) ( (lv_right_11_0= ruleOperandFragment ) ) )*
            {
            // InternalSqlParser.g:5305:2: ( (lv_op1_0_0= ruleOperandFragment ) )
            // InternalSqlParser.g:5306:1: (lv_op1_0_0= ruleOperandFragment )
            {
            // InternalSqlParser.g:5306:1: (lv_op1_0_0= ruleOperandFragment )
            // InternalSqlParser.g:5307:3: lv_op1_0_0= ruleOperandFragment
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getOperandAccess().getOp1OperandFragmentParserRuleCall_0_0()); 
              	    
            }
            pushFollow(FOLLOW_75);
            lv_op1_0_0=ruleOperandFragment();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getOperandRule());
              	        }
                     		set(
                     			current, 
                     			"op1",
                      		lv_op1_0_0, 
                      		"com.jaspersoft.studio.data.Sql.OperandFragment");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:5323:2: ( ( ( () otherlv_2= PlusSign ) | ( () otherlv_4= HyphenMinus ) | ( () otherlv_6= VerticalLineVerticalLine ) | ( () this_STAR_8= RULE_STAR ) | ( () otherlv_10= Solidus ) ) ( (lv_right_11_0= ruleOperandFragment ) ) )*
            loop101:
            do {
                int alt101=2;
                int LA101_0 = input.LA(1);

                if ( (LA101_0==VerticalLineVerticalLine||LA101_0==PlusSign||LA101_0==HyphenMinus||LA101_0==Solidus||LA101_0==RULE_STAR) ) {
                    alt101=1;
                }


                switch (alt101) {
            	case 1 :
            	    // InternalSqlParser.g:5323:3: ( ( () otherlv_2= PlusSign ) | ( () otherlv_4= HyphenMinus ) | ( () otherlv_6= VerticalLineVerticalLine ) | ( () this_STAR_8= RULE_STAR ) | ( () otherlv_10= Solidus ) ) ( (lv_right_11_0= ruleOperandFragment ) )
            	    {
            	    // InternalSqlParser.g:5323:3: ( ( () otherlv_2= PlusSign ) | ( () otherlv_4= HyphenMinus ) | ( () otherlv_6= VerticalLineVerticalLine ) | ( () this_STAR_8= RULE_STAR ) | ( () otherlv_10= Solidus ) )
            	    int alt100=5;
            	    switch ( input.LA(1) ) {
            	    case PlusSign:
            	        {
            	        alt100=1;
            	        }
            	        break;
            	    case HyphenMinus:
            	        {
            	        alt100=2;
            	        }
            	        break;
            	    case VerticalLineVerticalLine:
            	        {
            	        alt100=3;
            	        }
            	        break;
            	    case RULE_STAR:
            	        {
            	        alt100=4;
            	        }
            	        break;
            	    case Solidus:
            	        {
            	        alt100=5;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 100, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt100) {
            	        case 1 :
            	            // InternalSqlParser.g:5323:4: ( () otherlv_2= PlusSign )
            	            {
            	            // InternalSqlParser.g:5323:4: ( () otherlv_2= PlusSign )
            	            // InternalSqlParser.g:5323:5: () otherlv_2= PlusSign
            	            {
            	            // InternalSqlParser.g:5323:5: ()
            	            // InternalSqlParser.g:5324:2: 
            	            {
            	            if ( state.backtracking==0 ) {
            	               
            	              	  /* */ 
            	              	
            	            }
            	            if ( state.backtracking==0 ) {

            	                      current = forceCreateModelElementAndSet(
            	                          grammarAccess.getOperandAccess().getPlusLeftAction_1_0_0_0(),
            	                          current);
            	                  
            	            }

            	            }

            	            otherlv_2=(Token)match(input,PlusSign,FOLLOW_60); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	                  	newLeafNode(otherlv_2, grammarAccess.getOperandAccess().getPlusSignKeyword_1_0_0_1());
            	                  
            	            }

            	            }


            	            }
            	            break;
            	        case 2 :
            	            // InternalSqlParser.g:5338:6: ( () otherlv_4= HyphenMinus )
            	            {
            	            // InternalSqlParser.g:5338:6: ( () otherlv_4= HyphenMinus )
            	            // InternalSqlParser.g:5338:7: () otherlv_4= HyphenMinus
            	            {
            	            // InternalSqlParser.g:5338:7: ()
            	            // InternalSqlParser.g:5339:2: 
            	            {
            	            if ( state.backtracking==0 ) {
            	               
            	              	  /* */ 
            	              	
            	            }
            	            if ( state.backtracking==0 ) {

            	                      current = forceCreateModelElementAndSet(
            	                          grammarAccess.getOperandAccess().getMinusLeftAction_1_0_1_0(),
            	                          current);
            	                  
            	            }

            	            }

            	            otherlv_4=(Token)match(input,HyphenMinus,FOLLOW_60); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	                  	newLeafNode(otherlv_4, grammarAccess.getOperandAccess().getHyphenMinusKeyword_1_0_1_1());
            	                  
            	            }

            	            }


            	            }
            	            break;
            	        case 3 :
            	            // InternalSqlParser.g:5353:6: ( () otherlv_6= VerticalLineVerticalLine )
            	            {
            	            // InternalSqlParser.g:5353:6: ( () otherlv_6= VerticalLineVerticalLine )
            	            // InternalSqlParser.g:5353:7: () otherlv_6= VerticalLineVerticalLine
            	            {
            	            // InternalSqlParser.g:5353:7: ()
            	            // InternalSqlParser.g:5354:2: 
            	            {
            	            if ( state.backtracking==0 ) {
            	               
            	              	  /* */ 
            	              	
            	            }
            	            if ( state.backtracking==0 ) {

            	                      current = forceCreateModelElementAndSet(
            	                          grammarAccess.getOperandAccess().getConcatLeftAction_1_0_2_0(),
            	                          current);
            	                  
            	            }

            	            }

            	            otherlv_6=(Token)match(input,VerticalLineVerticalLine,FOLLOW_60); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	                  	newLeafNode(otherlv_6, grammarAccess.getOperandAccess().getVerticalLineVerticalLineKeyword_1_0_2_1());
            	                  
            	            }

            	            }


            	            }
            	            break;
            	        case 4 :
            	            // InternalSqlParser.g:5368:6: ( () this_STAR_8= RULE_STAR )
            	            {
            	            // InternalSqlParser.g:5368:6: ( () this_STAR_8= RULE_STAR )
            	            // InternalSqlParser.g:5368:7: () this_STAR_8= RULE_STAR
            	            {
            	            // InternalSqlParser.g:5368:7: ()
            	            // InternalSqlParser.g:5369:2: 
            	            {
            	            if ( state.backtracking==0 ) {
            	               
            	              	  /* */ 
            	              	
            	            }
            	            if ( state.backtracking==0 ) {

            	                      current = forceCreateModelElementAndSet(
            	                          grammarAccess.getOperandAccess().getMultiplyLeftAction_1_0_3_0(),
            	                          current);
            	                  
            	            }

            	            }

            	            this_STAR_8=(Token)match(input,RULE_STAR,FOLLOW_60); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {
            	               
            	                  newLeafNode(this_STAR_8, grammarAccess.getOperandAccess().getSTARTerminalRuleCall_1_0_3_1()); 
            	                  
            	            }

            	            }


            	            }
            	            break;
            	        case 5 :
            	            // InternalSqlParser.g:5382:6: ( () otherlv_10= Solidus )
            	            {
            	            // InternalSqlParser.g:5382:6: ( () otherlv_10= Solidus )
            	            // InternalSqlParser.g:5382:7: () otherlv_10= Solidus
            	            {
            	            // InternalSqlParser.g:5382:7: ()
            	            // InternalSqlParser.g:5383:2: 
            	            {
            	            if ( state.backtracking==0 ) {
            	               
            	              	  /* */ 
            	              	
            	            }
            	            if ( state.backtracking==0 ) {

            	                      current = forceCreateModelElementAndSet(
            	                          grammarAccess.getOperandAccess().getDivisionLeftAction_1_0_4_0(),
            	                          current);
            	                  
            	            }

            	            }

            	            otherlv_10=(Token)match(input,Solidus,FOLLOW_60); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	                  	newLeafNode(otherlv_10, grammarAccess.getOperandAccess().getSolidusKeyword_1_0_4_1());
            	                  
            	            }

            	            }


            	            }
            	            break;

            	    }

            	    // InternalSqlParser.g:5396:3: ( (lv_right_11_0= ruleOperandFragment ) )
            	    // InternalSqlParser.g:5397:1: (lv_right_11_0= ruleOperandFragment )
            	    {
            	    // InternalSqlParser.g:5397:1: (lv_right_11_0= ruleOperandFragment )
            	    // InternalSqlParser.g:5398:3: lv_right_11_0= ruleOperandFragment
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getOperandAccess().getRightOperandFragmentParserRuleCall_1_1_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_75);
            	    lv_right_11_0=ruleOperandFragment();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getOperandRule());
            	      	        }
            	             		set(
            	             			current, 
            	             			"right",
            	              		lv_right_11_0, 
            	              		"com.jaspersoft.studio.data.Sql.OperandFragment");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop101;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOperand"


    // $ANTLR start "entryRuleOperandFragment"
    // InternalSqlParser.g:5422:1: entryRuleOperandFragment returns [EObject current=null] : iv_ruleOperandFragment= ruleOperandFragment EOF ;
    public final EObject entryRuleOperandFragment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOperandFragment = null;


        try {
            // InternalSqlParser.g:5423:2: (iv_ruleOperandFragment= ruleOperandFragment EOF )
            // InternalSqlParser.g:5424:2: iv_ruleOperandFragment= ruleOperandFragment EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOperandFragmentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOperandFragment=ruleOperandFragment();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOperandFragment; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOperandFragment"


    // $ANTLR start "ruleOperandFragment"
    // InternalSqlParser.g:5431:1: ruleOperandFragment returns [EObject current=null] : ( ( (lv_column_0_0= ruleColumnOperand ) ) | ( (lv_xop_1_0= ruleXOperandFragment ) ) | ( (lv_subq_2_0= ruleSubQueryOperand ) ) | ( (lv_fcast_3_0= ruleOpFunctionCast ) ) | ( (lv_fext_4_0= ruleFunctionExtract ) ) | ( (lv_func_5_0= ruleOperandFunction ) ) | ( (lv_sqlcase_6_0= ruleSQLCASE ) ) ) ;
    public final EObject ruleOperandFragment() throws RecognitionException {
        EObject current = null;

        EObject lv_column_0_0 = null;

        EObject lv_xop_1_0 = null;

        EObject lv_subq_2_0 = null;

        EObject lv_fcast_3_0 = null;

        EObject lv_fext_4_0 = null;

        EObject lv_func_5_0 = null;

        EObject lv_sqlcase_6_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:5434:28: ( ( ( (lv_column_0_0= ruleColumnOperand ) ) | ( (lv_xop_1_0= ruleXOperandFragment ) ) | ( (lv_subq_2_0= ruleSubQueryOperand ) ) | ( (lv_fcast_3_0= ruleOpFunctionCast ) ) | ( (lv_fext_4_0= ruleFunctionExtract ) ) | ( (lv_func_5_0= ruleOperandFunction ) ) | ( (lv_sqlcase_6_0= ruleSQLCASE ) ) ) )
            // InternalSqlParser.g:5435:1: ( ( (lv_column_0_0= ruleColumnOperand ) ) | ( (lv_xop_1_0= ruleXOperandFragment ) ) | ( (lv_subq_2_0= ruleSubQueryOperand ) ) | ( (lv_fcast_3_0= ruleOpFunctionCast ) ) | ( (lv_fext_4_0= ruleFunctionExtract ) ) | ( (lv_func_5_0= ruleOperandFunction ) ) | ( (lv_sqlcase_6_0= ruleSQLCASE ) ) )
            {
            // InternalSqlParser.g:5435:1: ( ( (lv_column_0_0= ruleColumnOperand ) ) | ( (lv_xop_1_0= ruleXOperandFragment ) ) | ( (lv_subq_2_0= ruleSubQueryOperand ) ) | ( (lv_fcast_3_0= ruleOpFunctionCast ) ) | ( (lv_fext_4_0= ruleFunctionExtract ) ) | ( (lv_func_5_0= ruleOperandFunction ) ) | ( (lv_sqlcase_6_0= ruleSQLCASE ) ) )
            int alt102=7;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA102_1 = input.LA(2);

                if ( (LA102_1==LeftParenthesis) ) {
                    alt102=6;
                }
                else if ( (LA102_1==EOF||LA102_1==STRAIGHT_JOIN||(LA102_1>=KW_FOLLOWING && LA102_1<=INTERSECT)||LA102_1==PRECEDING||LA102_1==BETWEEN||LA102_1==NATURAL||(LA102_1>=EXCEPT && LA102_1<=HAVING)||LA102_1==OFFSET||(LA102_1>=CROSS && LA102_1<=FETCH)||(LA102_1>=GROUP && LA102_1<=MINUS)||(LA102_1>=NULLS && LA102_1<=ORDER)||(LA102_1>=RANGE && LA102_1<=UNION)||LA102_1==WHERE||(LA102_1>=DESC && LA102_1<=FULL)||LA102_1==JOIN||(LA102_1>=LEFT && LA102_1<=LIKE)||LA102_1==ROWS||LA102_1==THEN||LA102_1==WHEN||LA102_1==LeftParenthesisPlusSignRightParenthesis||LA102_1==AND||LA102_1==ASC||LA102_1==END||LA102_1==NOT||LA102_1==ExclamationMarkEqualsSign||(LA102_1>=LessThanSignEqualsSign && LA102_1<=AS)||(LA102_1>=IN && LA102_1<=IS)||(LA102_1>=OR && LA102_1<=VerticalLineVerticalLine)||(LA102_1>=RightParenthesis && LA102_1<=RightCurlyBracket)||(LA102_1>=RULE_JRNPARAM && LA102_1<=RULE_STAR)||(LA102_1>=RULE_STRING && LA102_1<=RULE_ID)) ) {
                    alt102=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 102, 1, input);

                    throw nvae;
                }
                }
                break;
            case RULE_STRING:
            case RULE_DBNAME:
                {
                alt102=1;
                }
                break;
            case RULE_JRPARAM:
            case RULE_JRNPARAM:
            case RULE_UNSIGNED:
            case RULE_INT:
            case RULE_SIGNED_DOUBLE:
            case RULE_STRING_:
                {
                alt102=2;
                }
                break;
            case LeftParenthesis:
                {
                alt102=3;
                }
                break;
            case CAST:
                {
                alt102=4;
                }
                break;
            case EXTRACT:
                {
                alt102=5;
                }
                break;
            case CASE:
                {
                alt102=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 102, 0, input);

                throw nvae;
            }

            switch (alt102) {
                case 1 :
                    // InternalSqlParser.g:5435:2: ( (lv_column_0_0= ruleColumnOperand ) )
                    {
                    // InternalSqlParser.g:5435:2: ( (lv_column_0_0= ruleColumnOperand ) )
                    // InternalSqlParser.g:5436:1: (lv_column_0_0= ruleColumnOperand )
                    {
                    // InternalSqlParser.g:5436:1: (lv_column_0_0= ruleColumnOperand )
                    // InternalSqlParser.g:5437:3: lv_column_0_0= ruleColumnOperand
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getOperandFragmentAccess().getColumnColumnOperandParserRuleCall_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_column_0_0=ruleColumnOperand();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getOperandFragmentRule());
                      	        }
                             		set(
                             			current, 
                             			"column",
                              		lv_column_0_0, 
                              		"com.jaspersoft.studio.data.Sql.ColumnOperand");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:5454:6: ( (lv_xop_1_0= ruleXOperandFragment ) )
                    {
                    // InternalSqlParser.g:5454:6: ( (lv_xop_1_0= ruleXOperandFragment ) )
                    // InternalSqlParser.g:5455:1: (lv_xop_1_0= ruleXOperandFragment )
                    {
                    // InternalSqlParser.g:5455:1: (lv_xop_1_0= ruleXOperandFragment )
                    // InternalSqlParser.g:5456:3: lv_xop_1_0= ruleXOperandFragment
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getOperandFragmentAccess().getXopXOperandFragmentParserRuleCall_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_xop_1_0=ruleXOperandFragment();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getOperandFragmentRule());
                      	        }
                             		set(
                             			current, 
                             			"xop",
                              		lv_xop_1_0, 
                              		"com.jaspersoft.studio.data.Sql.XOperandFragment");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSqlParser.g:5473:6: ( (lv_subq_2_0= ruleSubQueryOperand ) )
                    {
                    // InternalSqlParser.g:5473:6: ( (lv_subq_2_0= ruleSubQueryOperand ) )
                    // InternalSqlParser.g:5474:1: (lv_subq_2_0= ruleSubQueryOperand )
                    {
                    // InternalSqlParser.g:5474:1: (lv_subq_2_0= ruleSubQueryOperand )
                    // InternalSqlParser.g:5475:3: lv_subq_2_0= ruleSubQueryOperand
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getOperandFragmentAccess().getSubqSubQueryOperandParserRuleCall_2_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_subq_2_0=ruleSubQueryOperand();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getOperandFragmentRule());
                      	        }
                             		set(
                             			current, 
                             			"subq",
                              		lv_subq_2_0, 
                              		"com.jaspersoft.studio.data.Sql.SubQueryOperand");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalSqlParser.g:5492:6: ( (lv_fcast_3_0= ruleOpFunctionCast ) )
                    {
                    // InternalSqlParser.g:5492:6: ( (lv_fcast_3_0= ruleOpFunctionCast ) )
                    // InternalSqlParser.g:5493:1: (lv_fcast_3_0= ruleOpFunctionCast )
                    {
                    // InternalSqlParser.g:5493:1: (lv_fcast_3_0= ruleOpFunctionCast )
                    // InternalSqlParser.g:5494:3: lv_fcast_3_0= ruleOpFunctionCast
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getOperandFragmentAccess().getFcastOpFunctionCastParserRuleCall_3_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_fcast_3_0=ruleOpFunctionCast();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getOperandFragmentRule());
                      	        }
                             		set(
                             			current, 
                             			"fcast",
                              		lv_fcast_3_0, 
                              		"com.jaspersoft.studio.data.Sql.OpFunctionCast");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalSqlParser.g:5511:6: ( (lv_fext_4_0= ruleFunctionExtract ) )
                    {
                    // InternalSqlParser.g:5511:6: ( (lv_fext_4_0= ruleFunctionExtract ) )
                    // InternalSqlParser.g:5512:1: (lv_fext_4_0= ruleFunctionExtract )
                    {
                    // InternalSqlParser.g:5512:1: (lv_fext_4_0= ruleFunctionExtract )
                    // InternalSqlParser.g:5513:3: lv_fext_4_0= ruleFunctionExtract
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getOperandFragmentAccess().getFextFunctionExtractParserRuleCall_4_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_fext_4_0=ruleFunctionExtract();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getOperandFragmentRule());
                      	        }
                             		set(
                             			current, 
                             			"fext",
                              		lv_fext_4_0, 
                              		"com.jaspersoft.studio.data.Sql.FunctionExtract");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalSqlParser.g:5530:6: ( (lv_func_5_0= ruleOperandFunction ) )
                    {
                    // InternalSqlParser.g:5530:6: ( (lv_func_5_0= ruleOperandFunction ) )
                    // InternalSqlParser.g:5531:1: (lv_func_5_0= ruleOperandFunction )
                    {
                    // InternalSqlParser.g:5531:1: (lv_func_5_0= ruleOperandFunction )
                    // InternalSqlParser.g:5532:3: lv_func_5_0= ruleOperandFunction
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getOperandFragmentAccess().getFuncOperandFunctionParserRuleCall_5_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_func_5_0=ruleOperandFunction();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getOperandFragmentRule());
                      	        }
                             		set(
                             			current, 
                             			"func",
                              		lv_func_5_0, 
                              		"com.jaspersoft.studio.data.Sql.OperandFunction");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalSqlParser.g:5549:6: ( (lv_sqlcase_6_0= ruleSQLCASE ) )
                    {
                    // InternalSqlParser.g:5549:6: ( (lv_sqlcase_6_0= ruleSQLCASE ) )
                    // InternalSqlParser.g:5550:1: (lv_sqlcase_6_0= ruleSQLCASE )
                    {
                    // InternalSqlParser.g:5550:1: (lv_sqlcase_6_0= ruleSQLCASE )
                    // InternalSqlParser.g:5551:3: lv_sqlcase_6_0= ruleSQLCASE
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getOperandFragmentAccess().getSqlcaseSQLCASEParserRuleCall_6_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_sqlcase_6_0=ruleSQLCASE();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getOperandFragmentRule());
                      	        }
                             		set(
                             			current, 
                             			"sqlcase",
                              		lv_sqlcase_6_0, 
                              		"com.jaspersoft.studio.data.Sql.SQLCASE");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOperandFragment"


    // $ANTLR start "entryRuleOperandFunction"
    // InternalSqlParser.g:5575:1: entryRuleOperandFunction returns [EObject current=null] : iv_ruleOperandFunction= ruleOperandFunction EOF ;
    public final EObject entryRuleOperandFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOperandFunction = null;


        try {
            // InternalSqlParser.g:5576:2: (iv_ruleOperandFunction= ruleOperandFunction EOF )
            // InternalSqlParser.g:5577:2: iv_ruleOperandFunction= ruleOperandFunction EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOperandFunctionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOperandFunction=ruleOperandFunction();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOperandFunction; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOperandFunction"


    // $ANTLR start "ruleOperandFunction"
    // InternalSqlParser.g:5584:1: ruleOperandFunction returns [EObject current=null] : ( () ( (lv_fname_1_0= ruleFNAME ) ) ( ( (lv_star_2_0= RULE_STAR ) ) | ( (lv_args_3_0= ruleOpFunctionArg ) ) )? otherlv_4= RightParenthesis ( (lv_fan_5_0= ruleFunctionAnalytical ) )? ) ;
    public final EObject ruleOperandFunction() throws RecognitionException {
        EObject current = null;

        Token lv_star_2_0=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_fname_1_0 = null;

        EObject lv_args_3_0 = null;

        EObject lv_fan_5_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:5587:28: ( ( () ( (lv_fname_1_0= ruleFNAME ) ) ( ( (lv_star_2_0= RULE_STAR ) ) | ( (lv_args_3_0= ruleOpFunctionArg ) ) )? otherlv_4= RightParenthesis ( (lv_fan_5_0= ruleFunctionAnalytical ) )? ) )
            // InternalSqlParser.g:5588:1: ( () ( (lv_fname_1_0= ruleFNAME ) ) ( ( (lv_star_2_0= RULE_STAR ) ) | ( (lv_args_3_0= ruleOpFunctionArg ) ) )? otherlv_4= RightParenthesis ( (lv_fan_5_0= ruleFunctionAnalytical ) )? )
            {
            // InternalSqlParser.g:5588:1: ( () ( (lv_fname_1_0= ruleFNAME ) ) ( ( (lv_star_2_0= RULE_STAR ) ) | ( (lv_args_3_0= ruleOpFunctionArg ) ) )? otherlv_4= RightParenthesis ( (lv_fan_5_0= ruleFunctionAnalytical ) )? )
            // InternalSqlParser.g:5588:2: () ( (lv_fname_1_0= ruleFNAME ) ) ( ( (lv_star_2_0= RULE_STAR ) ) | ( (lv_args_3_0= ruleOpFunctionArg ) ) )? otherlv_4= RightParenthesis ( (lv_fan_5_0= ruleFunctionAnalytical ) )?
            {
            // InternalSqlParser.g:5588:2: ()
            // InternalSqlParser.g:5589:2: 
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getOperandFunctionAccess().getOpFunctionAction_0(),
                          current);
                  
            }

            }

            // InternalSqlParser.g:5597:2: ( (lv_fname_1_0= ruleFNAME ) )
            // InternalSqlParser.g:5598:1: (lv_fname_1_0= ruleFNAME )
            {
            // InternalSqlParser.g:5598:1: (lv_fname_1_0= ruleFNAME )
            // InternalSqlParser.g:5599:3: lv_fname_1_0= ruleFNAME
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getOperandFunctionAccess().getFnameFNAMEParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_76);
            lv_fname_1_0=ruleFNAME();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getOperandFunctionRule());
              	        }
                     		set(
                     			current, 
                     			"fname",
                      		lv_fname_1_0, 
                      		"com.jaspersoft.studio.data.Sql.FNAME");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:5615:2: ( ( (lv_star_2_0= RULE_STAR ) ) | ( (lv_args_3_0= ruleOpFunctionArg ) ) )?
            int alt103=3;
            int LA103_0 = input.LA(1);

            if ( (LA103_0==RULE_STAR) ) {
                alt103=1;
            }
            else if ( (LA103_0==DISTINCT||LA103_0==EXTRACT||LA103_0==CAST||LA103_0==CASE||LA103_0==ALL||LA103_0==LeftParenthesis||(LA103_0>=RULE_JRPARAM && LA103_0<=RULE_JRNPARAM)||(LA103_0>=RULE_UNSIGNED && LA103_0<=RULE_SIGNED_DOUBLE)||(LA103_0>=RULE_STRING_ && LA103_0<=RULE_ID)) ) {
                alt103=2;
            }
            switch (alt103) {
                case 1 :
                    // InternalSqlParser.g:5615:3: ( (lv_star_2_0= RULE_STAR ) )
                    {
                    // InternalSqlParser.g:5615:3: ( (lv_star_2_0= RULE_STAR ) )
                    // InternalSqlParser.g:5616:1: (lv_star_2_0= RULE_STAR )
                    {
                    // InternalSqlParser.g:5616:1: (lv_star_2_0= RULE_STAR )
                    // InternalSqlParser.g:5617:3: lv_star_2_0= RULE_STAR
                    {
                    lv_star_2_0=(Token)match(input,RULE_STAR,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(lv_star_2_0, grammarAccess.getOperandFunctionAccess().getStarSTARTerminalRuleCall_2_0_0()); 
                      		
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getOperandFunctionRule());
                      	        }
                             		setWithLastConsumed(
                             			current, 
                             			"star",
                              		lv_star_2_0, 
                              		"com.jaspersoft.studio.data.Sql.STAR");
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:5634:6: ( (lv_args_3_0= ruleOpFunctionArg ) )
                    {
                    // InternalSqlParser.g:5634:6: ( (lv_args_3_0= ruleOpFunctionArg ) )
                    // InternalSqlParser.g:5635:1: (lv_args_3_0= ruleOpFunctionArg )
                    {
                    // InternalSqlParser.g:5635:1: (lv_args_3_0= ruleOpFunctionArg )
                    // InternalSqlParser.g:5636:3: lv_args_3_0= ruleOpFunctionArg
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getOperandFunctionAccess().getArgsOpFunctionArgParserRuleCall_2_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_8);
                    lv_args_3_0=ruleOpFunctionArg();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getOperandFunctionRule());
                      	        }
                             		set(
                             			current, 
                             			"args",
                              		lv_args_3_0, 
                              		"com.jaspersoft.studio.data.Sql.OpFunctionArg");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,RightParenthesis,FOLLOW_77); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_4, grammarAccess.getOperandFunctionAccess().getRightParenthesisKeyword_3());
                  
            }
            // InternalSqlParser.g:5657:1: ( (lv_fan_5_0= ruleFunctionAnalytical ) )?
            int alt104=2;
            int LA104_0 = input.LA(1);

            if ( (LA104_0==OVER) ) {
                alt104=1;
            }
            switch (alt104) {
                case 1 :
                    // InternalSqlParser.g:5658:1: (lv_fan_5_0= ruleFunctionAnalytical )
                    {
                    // InternalSqlParser.g:5658:1: (lv_fan_5_0= ruleFunctionAnalytical )
                    // InternalSqlParser.g:5659:3: lv_fan_5_0= ruleFunctionAnalytical
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getOperandFunctionAccess().getFanFunctionAnalyticalParserRuleCall_4_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_fan_5_0=ruleFunctionAnalytical();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getOperandFunctionRule());
                      	        }
                             		set(
                             			current, 
                             			"fan",
                              		lv_fan_5_0, 
                              		"com.jaspersoft.studio.data.Sql.FunctionAnalytical");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOperandFunction"


    // $ANTLR start "entryRuleFunctionExtract"
    // InternalSqlParser.g:5683:1: entryRuleFunctionExtract returns [EObject current=null] : iv_ruleFunctionExtract= ruleFunctionExtract EOF ;
    public final EObject entryRuleFunctionExtract() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionExtract = null;


        try {
            // InternalSqlParser.g:5684:2: (iv_ruleFunctionExtract= ruleFunctionExtract EOF )
            // InternalSqlParser.g:5685:2: iv_ruleFunctionExtract= ruleFunctionExtract EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFunctionExtractRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleFunctionExtract=ruleFunctionExtract();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFunctionExtract; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFunctionExtract"


    // $ANTLR start "ruleFunctionExtract"
    // InternalSqlParser.g:5692:1: ruleFunctionExtract returns [EObject current=null] : (otherlv_0= EXTRACT otherlv_1= LeftParenthesis ( (lv_v_2_0= ruleEXTRACT_VALUES ) ) otherlv_3= FROM ( (lv_operand_4_0= ruleOperandGroup ) ) otherlv_5= RightParenthesis ) ;
    public final EObject ruleFunctionExtract() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Enumerator lv_v_2_0 = null;

        EObject lv_operand_4_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:5695:28: ( (otherlv_0= EXTRACT otherlv_1= LeftParenthesis ( (lv_v_2_0= ruleEXTRACT_VALUES ) ) otherlv_3= FROM ( (lv_operand_4_0= ruleOperandGroup ) ) otherlv_5= RightParenthesis ) )
            // InternalSqlParser.g:5696:1: (otherlv_0= EXTRACT otherlv_1= LeftParenthesis ( (lv_v_2_0= ruleEXTRACT_VALUES ) ) otherlv_3= FROM ( (lv_operand_4_0= ruleOperandGroup ) ) otherlv_5= RightParenthesis )
            {
            // InternalSqlParser.g:5696:1: (otherlv_0= EXTRACT otherlv_1= LeftParenthesis ( (lv_v_2_0= ruleEXTRACT_VALUES ) ) otherlv_3= FROM ( (lv_operand_4_0= ruleOperandGroup ) ) otherlv_5= RightParenthesis )
            // InternalSqlParser.g:5697:2: otherlv_0= EXTRACT otherlv_1= LeftParenthesis ( (lv_v_2_0= ruleEXTRACT_VALUES ) ) otherlv_3= FROM ( (lv_operand_4_0= ruleOperandGroup ) ) otherlv_5= RightParenthesis
            {
            otherlv_0=(Token)match(input,EXTRACT,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getFunctionExtractAccess().getEXTRACTKeyword_0());
                  
            }
            otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_78); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getFunctionExtractAccess().getLeftParenthesisKeyword_1());
                  
            }
            // InternalSqlParser.g:5706:1: ( (lv_v_2_0= ruleEXTRACT_VALUES ) )
            // InternalSqlParser.g:5707:1: (lv_v_2_0= ruleEXTRACT_VALUES )
            {
            // InternalSqlParser.g:5707:1: (lv_v_2_0= ruleEXTRACT_VALUES )
            // InternalSqlParser.g:5708:3: lv_v_2_0= ruleEXTRACT_VALUES
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getFunctionExtractAccess().getVEXTRACT_VALUESEnumRuleCall_2_0()); 
              	    
            }
            pushFollow(FOLLOW_20);
            lv_v_2_0=ruleEXTRACT_VALUES();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getFunctionExtractRule());
              	        }
                     		set(
                     			current, 
                     			"v",
                      		lv_v_2_0, 
                      		"com.jaspersoft.studio.data.Sql.EXTRACT_VALUES");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_3=(Token)match(input,FROM,FOLLOW_60); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getFunctionExtractAccess().getFROMKeyword_3());
                  
            }
            // InternalSqlParser.g:5729:1: ( (lv_operand_4_0= ruleOperandGroup ) )
            // InternalSqlParser.g:5730:1: (lv_operand_4_0= ruleOperandGroup )
            {
            // InternalSqlParser.g:5730:1: (lv_operand_4_0= ruleOperandGroup )
            // InternalSqlParser.g:5731:3: lv_operand_4_0= ruleOperandGroup
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getFunctionExtractAccess().getOperandOperandGroupParserRuleCall_4_0()); 
              	    
            }
            pushFollow(FOLLOW_8);
            lv_operand_4_0=ruleOperandGroup();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getFunctionExtractRule());
              	        }
                     		set(
                     			current, 
                     			"operand",
                      		lv_operand_4_0, 
                      		"com.jaspersoft.studio.data.Sql.OperandGroup");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_5=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_5, grammarAccess.getFunctionExtractAccess().getRightParenthesisKeyword_5());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFunctionExtract"


    // $ANTLR start "entryRuleFunctionAnalytical"
    // InternalSqlParser.g:5760:1: entryRuleFunctionAnalytical returns [EObject current=null] : iv_ruleFunctionAnalytical= ruleFunctionAnalytical EOF ;
    public final EObject entryRuleFunctionAnalytical() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionAnalytical = null;


        try {
            // InternalSqlParser.g:5761:2: (iv_ruleFunctionAnalytical= ruleFunctionAnalytical EOF )
            // InternalSqlParser.g:5762:2: iv_ruleFunctionAnalytical= ruleFunctionAnalytical EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFunctionAnalyticalRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleFunctionAnalytical=ruleFunctionAnalytical();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFunctionAnalytical; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFunctionAnalytical"


    // $ANTLR start "ruleFunctionAnalytical"
    // InternalSqlParser.g:5769:1: ruleFunctionAnalytical returns [EObject current=null] : (otherlv_0= OVER otherlv_1= LeftParenthesis ( (lv_anClause_2_0= ruleAnalyticClause ) ) otherlv_3= RightParenthesis ) ;
    public final EObject ruleFunctionAnalytical() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_anClause_2_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:5772:28: ( (otherlv_0= OVER otherlv_1= LeftParenthesis ( (lv_anClause_2_0= ruleAnalyticClause ) ) otherlv_3= RightParenthesis ) )
            // InternalSqlParser.g:5773:1: (otherlv_0= OVER otherlv_1= LeftParenthesis ( (lv_anClause_2_0= ruleAnalyticClause ) ) otherlv_3= RightParenthesis )
            {
            // InternalSqlParser.g:5773:1: (otherlv_0= OVER otherlv_1= LeftParenthesis ( (lv_anClause_2_0= ruleAnalyticClause ) ) otherlv_3= RightParenthesis )
            // InternalSqlParser.g:5774:2: otherlv_0= OVER otherlv_1= LeftParenthesis ( (lv_anClause_2_0= ruleAnalyticClause ) ) otherlv_3= RightParenthesis
            {
            otherlv_0=(Token)match(input,OVER,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getFunctionAnalyticalAccess().getOVERKeyword_0());
                  
            }
            otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_79); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getFunctionAnalyticalAccess().getLeftParenthesisKeyword_1());
                  
            }
            // InternalSqlParser.g:5783:1: ( (lv_anClause_2_0= ruleAnalyticClause ) )
            // InternalSqlParser.g:5784:1: (lv_anClause_2_0= ruleAnalyticClause )
            {
            // InternalSqlParser.g:5784:1: (lv_anClause_2_0= ruleAnalyticClause )
            // InternalSqlParser.g:5785:3: lv_anClause_2_0= ruleAnalyticClause
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getFunctionAnalyticalAccess().getAnClauseAnalyticClauseParserRuleCall_2_0()); 
              	    
            }
            pushFollow(FOLLOW_8);
            lv_anClause_2_0=ruleAnalyticClause();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getFunctionAnalyticalRule());
              	        }
                     		set(
                     			current, 
                     			"anClause",
                      		lv_anClause_2_0, 
                      		"com.jaspersoft.studio.data.Sql.AnalyticClause");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_3=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getFunctionAnalyticalAccess().getRightParenthesisKeyword_3());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFunctionAnalytical"


    // $ANTLR start "entryRuleAnalyticClause"
    // InternalSqlParser.g:5814:1: entryRuleAnalyticClause returns [EObject current=null] : iv_ruleAnalyticClause= ruleAnalyticClause EOF ;
    public final EObject entryRuleAnalyticClause() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnalyticClause = null;


        try {
            // InternalSqlParser.g:5815:2: (iv_ruleAnalyticClause= ruleAnalyticClause EOF )
            // InternalSqlParser.g:5816:2: iv_ruleAnalyticClause= ruleAnalyticClause EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAnalyticClauseRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAnalyticClause=ruleAnalyticClause();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAnalyticClause; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAnalyticClause"


    // $ANTLR start "ruleAnalyticClause"
    // InternalSqlParser.g:5823:1: ruleAnalyticClause returns [EObject current=null] : ( () ( (lv_abc_1_0= ruleQueryPartitionClause ) )? ( ( (lv_obc_2_0= ruleOrderByClause ) ) ( (lv_winc_3_0= ruleWindowingClause ) )? )? ) ;
    public final EObject ruleAnalyticClause() throws RecognitionException {
        EObject current = null;

        EObject lv_abc_1_0 = null;

        EObject lv_obc_2_0 = null;

        EObject lv_winc_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:5826:28: ( ( () ( (lv_abc_1_0= ruleQueryPartitionClause ) )? ( ( (lv_obc_2_0= ruleOrderByClause ) ) ( (lv_winc_3_0= ruleWindowingClause ) )? )? ) )
            // InternalSqlParser.g:5827:1: ( () ( (lv_abc_1_0= ruleQueryPartitionClause ) )? ( ( (lv_obc_2_0= ruleOrderByClause ) ) ( (lv_winc_3_0= ruleWindowingClause ) )? )? )
            {
            // InternalSqlParser.g:5827:1: ( () ( (lv_abc_1_0= ruleQueryPartitionClause ) )? ( ( (lv_obc_2_0= ruleOrderByClause ) ) ( (lv_winc_3_0= ruleWindowingClause ) )? )? )
            // InternalSqlParser.g:5827:2: () ( (lv_abc_1_0= ruleQueryPartitionClause ) )? ( ( (lv_obc_2_0= ruleOrderByClause ) ) ( (lv_winc_3_0= ruleWindowingClause ) )? )?
            {
            // InternalSqlParser.g:5827:2: ()
            // InternalSqlParser.g:5828:2: 
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getAnalyticClauseAccess().getAnalyticClauseAction_0(),
                          current);
                  
            }

            }

            // InternalSqlParser.g:5836:2: ( (lv_abc_1_0= ruleQueryPartitionClause ) )?
            int alt105=2;
            int LA105_0 = input.LA(1);

            if ( (LA105_0==PARTITION) ) {
                alt105=1;
            }
            switch (alt105) {
                case 1 :
                    // InternalSqlParser.g:5837:1: (lv_abc_1_0= ruleQueryPartitionClause )
                    {
                    // InternalSqlParser.g:5837:1: (lv_abc_1_0= ruleQueryPartitionClause )
                    // InternalSqlParser.g:5838:3: lv_abc_1_0= ruleQueryPartitionClause
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getAnalyticClauseAccess().getAbcQueryPartitionClauseParserRuleCall_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_80);
                    lv_abc_1_0=ruleQueryPartitionClause();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getAnalyticClauseRule());
                      	        }
                             		set(
                             			current, 
                             			"abc",
                              		lv_abc_1_0, 
                              		"com.jaspersoft.studio.data.Sql.QueryPartitionClause");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }
                    break;

            }

            // InternalSqlParser.g:5854:3: ( ( (lv_obc_2_0= ruleOrderByClause ) ) ( (lv_winc_3_0= ruleWindowingClause ) )? )?
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==ORDER) ) {
                alt107=1;
            }
            switch (alt107) {
                case 1 :
                    // InternalSqlParser.g:5854:4: ( (lv_obc_2_0= ruleOrderByClause ) ) ( (lv_winc_3_0= ruleWindowingClause ) )?
                    {
                    // InternalSqlParser.g:5854:4: ( (lv_obc_2_0= ruleOrderByClause ) )
                    // InternalSqlParser.g:5855:1: (lv_obc_2_0= ruleOrderByClause )
                    {
                    // InternalSqlParser.g:5855:1: (lv_obc_2_0= ruleOrderByClause )
                    // InternalSqlParser.g:5856:3: lv_obc_2_0= ruleOrderByClause
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getAnalyticClauseAccess().getObcOrderByClauseParserRuleCall_2_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_81);
                    lv_obc_2_0=ruleOrderByClause();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getAnalyticClauseRule());
                      	        }
                             		set(
                             			current, 
                             			"obc",
                              		lv_obc_2_0, 
                              		"com.jaspersoft.studio.data.Sql.OrderByClause");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }

                    // InternalSqlParser.g:5872:2: ( (lv_winc_3_0= ruleWindowingClause ) )?
                    int alt106=2;
                    int LA106_0 = input.LA(1);

                    if ( (LA106_0==RANGE||LA106_0==ROWS) ) {
                        alt106=1;
                    }
                    switch (alt106) {
                        case 1 :
                            // InternalSqlParser.g:5873:1: (lv_winc_3_0= ruleWindowingClause )
                            {
                            // InternalSqlParser.g:5873:1: (lv_winc_3_0= ruleWindowingClause )
                            // InternalSqlParser.g:5874:3: lv_winc_3_0= ruleWindowingClause
                            {
                            if ( state.backtracking==0 ) {
                               
                              	        newCompositeNode(grammarAccess.getAnalyticClauseAccess().getWincWindowingClauseParserRuleCall_2_1_0()); 
                              	    
                            }
                            pushFollow(FOLLOW_2);
                            lv_winc_3_0=ruleWindowingClause();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              	        if (current==null) {
                              	            current = createModelElementForParent(grammarAccess.getAnalyticClauseRule());
                              	        }
                                     		set(
                                     			current, 
                                     			"winc",
                                      		lv_winc_3_0, 
                                      		"com.jaspersoft.studio.data.Sql.WindowingClause");
                              	        afterParserOrEnumRuleCall();
                              	    
                            }

                            }


                            }
                            break;

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAnalyticClause"


    // $ANTLR start "entryRuleWindowingClause"
    // InternalSqlParser.g:5898:1: entryRuleWindowingClause returns [EObject current=null] : iv_ruleWindowingClause= ruleWindowingClause EOF ;
    public final EObject entryRuleWindowingClause() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWindowingClause = null;


        try {
            // InternalSqlParser.g:5899:2: (iv_ruleWindowingClause= ruleWindowingClause EOF )
            // InternalSqlParser.g:5900:2: iv_ruleWindowingClause= ruleWindowingClause EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getWindowingClauseRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleWindowingClause=ruleWindowingClause();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleWindowingClause; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleWindowingClause"


    // $ANTLR start "ruleWindowingClause"
    // InternalSqlParser.g:5907:1: ruleWindowingClause returns [EObject current=null] : ( (otherlv_0= ROWS | otherlv_1= RANGE ) (this_WindowingClauseBetween_2= ruleWindowingClauseBetween | this_WindowingClauseOperandPreceding_3= ruleWindowingClauseOperandPreceding ) ) ;
    public final EObject ruleWindowingClause() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        EObject this_WindowingClauseBetween_2 = null;

        EObject this_WindowingClauseOperandPreceding_3 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:5910:28: ( ( (otherlv_0= ROWS | otherlv_1= RANGE ) (this_WindowingClauseBetween_2= ruleWindowingClauseBetween | this_WindowingClauseOperandPreceding_3= ruleWindowingClauseOperandPreceding ) ) )
            // InternalSqlParser.g:5911:1: ( (otherlv_0= ROWS | otherlv_1= RANGE ) (this_WindowingClauseBetween_2= ruleWindowingClauseBetween | this_WindowingClauseOperandPreceding_3= ruleWindowingClauseOperandPreceding ) )
            {
            // InternalSqlParser.g:5911:1: ( (otherlv_0= ROWS | otherlv_1= RANGE ) (this_WindowingClauseBetween_2= ruleWindowingClauseBetween | this_WindowingClauseOperandPreceding_3= ruleWindowingClauseOperandPreceding ) )
            // InternalSqlParser.g:5911:2: (otherlv_0= ROWS | otherlv_1= RANGE ) (this_WindowingClauseBetween_2= ruleWindowingClauseBetween | this_WindowingClauseOperandPreceding_3= ruleWindowingClauseOperandPreceding )
            {
            // InternalSqlParser.g:5911:2: (otherlv_0= ROWS | otherlv_1= RANGE )
            int alt108=2;
            int LA108_0 = input.LA(1);

            if ( (LA108_0==ROWS) ) {
                alt108=1;
            }
            else if ( (LA108_0==RANGE) ) {
                alt108=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 108, 0, input);

                throw nvae;
            }
            switch (alt108) {
                case 1 :
                    // InternalSqlParser.g:5912:2: otherlv_0= ROWS
                    {
                    otherlv_0=(Token)match(input,ROWS,FOLLOW_82); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_0, grammarAccess.getWindowingClauseAccess().getROWSKeyword_0_0());
                          
                    }

                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:5918:2: otherlv_1= RANGE
                    {
                    otherlv_1=(Token)match(input,RANGE,FOLLOW_82); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_1, grammarAccess.getWindowingClauseAccess().getRANGEKeyword_0_1());
                          
                    }

                    }
                    break;

            }

            // InternalSqlParser.g:5922:2: (this_WindowingClauseBetween_2= ruleWindowingClauseBetween | this_WindowingClauseOperandPreceding_3= ruleWindowingClauseOperandPreceding )
            int alt109=2;
            int LA109_0 = input.LA(1);

            if ( (LA109_0==BETWEEN) ) {
                alt109=1;
            }
            else if ( (LA109_0==UNBOUNDED||LA109_0==CURRENT||LA109_0==EXTRACT||LA109_0==CAST||LA109_0==CASE||LA109_0==LeftParenthesis||(LA109_0>=RULE_JRPARAM && LA109_0<=RULE_JRNPARAM)||(LA109_0>=RULE_UNSIGNED && LA109_0<=RULE_SIGNED_DOUBLE)||(LA109_0>=RULE_STRING_ && LA109_0<=RULE_ID)) ) {
                alt109=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 109, 0, input);

                throw nvae;
            }
            switch (alt109) {
                case 1 :
                    // InternalSqlParser.g:5923:2: this_WindowingClauseBetween_2= ruleWindowingClauseBetween
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getWindowingClauseAccess().getWindowingClauseBetweenParserRuleCall_1_0()); 
                          
                    }
                    pushFollow(FOLLOW_2);
                    this_WindowingClauseBetween_2=ruleWindowingClauseBetween();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_WindowingClauseBetween_2;
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:5936:2: this_WindowingClauseOperandPreceding_3= ruleWindowingClauseOperandPreceding
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getWindowingClauseAccess().getWindowingClauseOperandPrecedingParserRuleCall_1_1()); 
                          
                    }
                    pushFollow(FOLLOW_2);
                    this_WindowingClauseOperandPreceding_3=ruleWindowingClauseOperandPreceding();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_WindowingClauseOperandPreceding_3;
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleWindowingClause"


    // $ANTLR start "entryRuleWindowingClauseBetween"
    // InternalSqlParser.g:5955:1: entryRuleWindowingClauseBetween returns [EObject current=null] : iv_ruleWindowingClauseBetween= ruleWindowingClauseBetween EOF ;
    public final EObject entryRuleWindowingClauseBetween() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWindowingClauseBetween = null;


        try {
            // InternalSqlParser.g:5956:2: (iv_ruleWindowingClauseBetween= ruleWindowingClauseBetween EOF )
            // InternalSqlParser.g:5957:2: iv_ruleWindowingClauseBetween= ruleWindowingClauseBetween EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getWindowingClauseBetweenRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleWindowingClauseBetween=ruleWindowingClauseBetween();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleWindowingClauseBetween; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleWindowingClauseBetween"


    // $ANTLR start "ruleWindowingClauseBetween"
    // InternalSqlParser.g:5964:1: ruleWindowingClauseBetween returns [EObject current=null] : (otherlv_0= BETWEEN ( (lv_wcoP_1_0= ruleWindowingClauseOperandPreceding ) ) otherlv_2= AND ( (lv_wcoF_3_0= ruleWindowingClauseOperandFollowing ) ) ) ;
    public final EObject ruleWindowingClauseBetween() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_wcoP_1_0 = null;

        EObject lv_wcoF_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:5967:28: ( (otherlv_0= BETWEEN ( (lv_wcoP_1_0= ruleWindowingClauseOperandPreceding ) ) otherlv_2= AND ( (lv_wcoF_3_0= ruleWindowingClauseOperandFollowing ) ) ) )
            // InternalSqlParser.g:5968:1: (otherlv_0= BETWEEN ( (lv_wcoP_1_0= ruleWindowingClauseOperandPreceding ) ) otherlv_2= AND ( (lv_wcoF_3_0= ruleWindowingClauseOperandFollowing ) ) )
            {
            // InternalSqlParser.g:5968:1: (otherlv_0= BETWEEN ( (lv_wcoP_1_0= ruleWindowingClauseOperandPreceding ) ) otherlv_2= AND ( (lv_wcoF_3_0= ruleWindowingClauseOperandFollowing ) ) )
            // InternalSqlParser.g:5969:2: otherlv_0= BETWEEN ( (lv_wcoP_1_0= ruleWindowingClauseOperandPreceding ) ) otherlv_2= AND ( (lv_wcoF_3_0= ruleWindowingClauseOperandFollowing ) )
            {
            otherlv_0=(Token)match(input,BETWEEN,FOLLOW_82); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getWindowingClauseBetweenAccess().getBETWEENKeyword_0());
                  
            }
            // InternalSqlParser.g:5973:1: ( (lv_wcoP_1_0= ruleWindowingClauseOperandPreceding ) )
            // InternalSqlParser.g:5974:1: (lv_wcoP_1_0= ruleWindowingClauseOperandPreceding )
            {
            // InternalSqlParser.g:5974:1: (lv_wcoP_1_0= ruleWindowingClauseOperandPreceding )
            // InternalSqlParser.g:5975:3: lv_wcoP_1_0= ruleWindowingClauseOperandPreceding
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getWindowingClauseBetweenAccess().getWcoPWindowingClauseOperandPrecedingParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_71);
            lv_wcoP_1_0=ruleWindowingClauseOperandPreceding();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getWindowingClauseBetweenRule());
              	        }
                     		set(
                     			current, 
                     			"wcoP",
                      		lv_wcoP_1_0, 
                      		"com.jaspersoft.studio.data.Sql.WindowingClauseOperandPreceding");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_2=(Token)match(input,AND,FOLLOW_82); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getWindowingClauseBetweenAccess().getANDKeyword_2());
                  
            }
            // InternalSqlParser.g:5996:1: ( (lv_wcoF_3_0= ruleWindowingClauseOperandFollowing ) )
            // InternalSqlParser.g:5997:1: (lv_wcoF_3_0= ruleWindowingClauseOperandFollowing )
            {
            // InternalSqlParser.g:5997:1: (lv_wcoF_3_0= ruleWindowingClauseOperandFollowing )
            // InternalSqlParser.g:5998:3: lv_wcoF_3_0= ruleWindowingClauseOperandFollowing
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getWindowingClauseBetweenAccess().getWcoFWindowingClauseOperandFollowingParserRuleCall_3_0()); 
              	    
            }
            pushFollow(FOLLOW_2);
            lv_wcoF_3_0=ruleWindowingClauseOperandFollowing();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getWindowingClauseBetweenRule());
              	        }
                     		set(
                     			current, 
                     			"wcoF",
                      		lv_wcoF_3_0, 
                      		"com.jaspersoft.studio.data.Sql.WindowingClauseOperandFollowing");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleWindowingClauseBetween"


    // $ANTLR start "entryRuleWindowingClauseOperandFollowing"
    // InternalSqlParser.g:6022:1: entryRuleWindowingClauseOperandFollowing returns [EObject current=null] : iv_ruleWindowingClauseOperandFollowing= ruleWindowingClauseOperandFollowing EOF ;
    public final EObject entryRuleWindowingClauseOperandFollowing() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWindowingClauseOperandFollowing = null;


        try {
            // InternalSqlParser.g:6023:2: (iv_ruleWindowingClauseOperandFollowing= ruleWindowingClauseOperandFollowing EOF )
            // InternalSqlParser.g:6024:2: iv_ruleWindowingClauseOperandFollowing= ruleWindowingClauseOperandFollowing EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getWindowingClauseOperandFollowingRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleWindowingClauseOperandFollowing=ruleWindowingClauseOperandFollowing();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleWindowingClauseOperandFollowing; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleWindowingClauseOperandFollowing"


    // $ANTLR start "ruleWindowingClauseOperandFollowing"
    // InternalSqlParser.g:6031:1: ruleWindowingClauseOperandFollowing returns [EObject current=null] : ( () ( (otherlv_1= UNBOUNDED otherlv_2= KW_FOLLOWING ) | (otherlv_3= CURRENT otherlv_4= ROW ) | ( ( (lv_exp_5_0= ruleAnalyticExprArg ) ) (otherlv_6= PRECEDING | otherlv_7= KW_FOLLOWING ) ) ) ) ;
    public final EObject ruleWindowingClauseOperandFollowing() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        EObject lv_exp_5_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:6034:28: ( ( () ( (otherlv_1= UNBOUNDED otherlv_2= KW_FOLLOWING ) | (otherlv_3= CURRENT otherlv_4= ROW ) | ( ( (lv_exp_5_0= ruleAnalyticExprArg ) ) (otherlv_6= PRECEDING | otherlv_7= KW_FOLLOWING ) ) ) ) )
            // InternalSqlParser.g:6035:1: ( () ( (otherlv_1= UNBOUNDED otherlv_2= KW_FOLLOWING ) | (otherlv_3= CURRENT otherlv_4= ROW ) | ( ( (lv_exp_5_0= ruleAnalyticExprArg ) ) (otherlv_6= PRECEDING | otherlv_7= KW_FOLLOWING ) ) ) )
            {
            // InternalSqlParser.g:6035:1: ( () ( (otherlv_1= UNBOUNDED otherlv_2= KW_FOLLOWING ) | (otherlv_3= CURRENT otherlv_4= ROW ) | ( ( (lv_exp_5_0= ruleAnalyticExprArg ) ) (otherlv_6= PRECEDING | otherlv_7= KW_FOLLOWING ) ) ) )
            // InternalSqlParser.g:6035:2: () ( (otherlv_1= UNBOUNDED otherlv_2= KW_FOLLOWING ) | (otherlv_3= CURRENT otherlv_4= ROW ) | ( ( (lv_exp_5_0= ruleAnalyticExprArg ) ) (otherlv_6= PRECEDING | otherlv_7= KW_FOLLOWING ) ) )
            {
            // InternalSqlParser.g:6035:2: ()
            // InternalSqlParser.g:6036:2: 
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getWindowingClauseOperandFollowingAccess().getWindowingClauseOperandFollowingAction_0(),
                          current);
                  
            }

            }

            // InternalSqlParser.g:6044:2: ( (otherlv_1= UNBOUNDED otherlv_2= KW_FOLLOWING ) | (otherlv_3= CURRENT otherlv_4= ROW ) | ( ( (lv_exp_5_0= ruleAnalyticExprArg ) ) (otherlv_6= PRECEDING | otherlv_7= KW_FOLLOWING ) ) )
            int alt111=3;
            switch ( input.LA(1) ) {
            case UNBOUNDED:
                {
                alt111=1;
                }
                break;
            case CURRENT:
                {
                alt111=2;
                }
                break;
            case EXTRACT:
            case CAST:
            case CASE:
            case LeftParenthesis:
            case RULE_JRPARAM:
            case RULE_JRNPARAM:
            case RULE_UNSIGNED:
            case RULE_INT:
            case RULE_SIGNED_DOUBLE:
            case RULE_STRING_:
            case RULE_STRING:
            case RULE_DBNAME:
            case RULE_ID:
                {
                alt111=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 111, 0, input);

                throw nvae;
            }

            switch (alt111) {
                case 1 :
                    // InternalSqlParser.g:6044:3: (otherlv_1= UNBOUNDED otherlv_2= KW_FOLLOWING )
                    {
                    // InternalSqlParser.g:6044:3: (otherlv_1= UNBOUNDED otherlv_2= KW_FOLLOWING )
                    // InternalSqlParser.g:6045:2: otherlv_1= UNBOUNDED otherlv_2= KW_FOLLOWING
                    {
                    otherlv_1=(Token)match(input,UNBOUNDED,FOLLOW_83); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_1, grammarAccess.getWindowingClauseOperandFollowingAccess().getUNBOUNDEDKeyword_1_0_0());
                          
                    }
                    otherlv_2=(Token)match(input,KW_FOLLOWING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_2, grammarAccess.getWindowingClauseOperandFollowingAccess().getFOLLOWINGKeyword_1_0_1());
                          
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:6055:6: (otherlv_3= CURRENT otherlv_4= ROW )
                    {
                    // InternalSqlParser.g:6055:6: (otherlv_3= CURRENT otherlv_4= ROW )
                    // InternalSqlParser.g:6056:2: otherlv_3= CURRENT otherlv_4= ROW
                    {
                    otherlv_3=(Token)match(input,CURRENT,FOLLOW_84); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_3, grammarAccess.getWindowingClauseOperandFollowingAccess().getCURRENTKeyword_1_1_0());
                          
                    }
                    otherlv_4=(Token)match(input,ROW,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_4, grammarAccess.getWindowingClauseOperandFollowingAccess().getROWKeyword_1_1_1());
                          
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSqlParser.g:6066:6: ( ( (lv_exp_5_0= ruleAnalyticExprArg ) ) (otherlv_6= PRECEDING | otherlv_7= KW_FOLLOWING ) )
                    {
                    // InternalSqlParser.g:6066:6: ( ( (lv_exp_5_0= ruleAnalyticExprArg ) ) (otherlv_6= PRECEDING | otherlv_7= KW_FOLLOWING ) )
                    // InternalSqlParser.g:6066:7: ( (lv_exp_5_0= ruleAnalyticExprArg ) ) (otherlv_6= PRECEDING | otherlv_7= KW_FOLLOWING )
                    {
                    // InternalSqlParser.g:6066:7: ( (lv_exp_5_0= ruleAnalyticExprArg ) )
                    // InternalSqlParser.g:6067:1: (lv_exp_5_0= ruleAnalyticExprArg )
                    {
                    // InternalSqlParser.g:6067:1: (lv_exp_5_0= ruleAnalyticExprArg )
                    // InternalSqlParser.g:6068:3: lv_exp_5_0= ruleAnalyticExprArg
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getWindowingClauseOperandFollowingAccess().getExpAnalyticExprArgParserRuleCall_1_2_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_85);
                    lv_exp_5_0=ruleAnalyticExprArg();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getWindowingClauseOperandFollowingRule());
                      	        }
                             		set(
                             			current, 
                             			"exp",
                              		lv_exp_5_0, 
                              		"com.jaspersoft.studio.data.Sql.AnalyticExprArg");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }

                    // InternalSqlParser.g:6084:2: (otherlv_6= PRECEDING | otherlv_7= KW_FOLLOWING )
                    int alt110=2;
                    int LA110_0 = input.LA(1);

                    if ( (LA110_0==PRECEDING) ) {
                        alt110=1;
                    }
                    else if ( (LA110_0==KW_FOLLOWING) ) {
                        alt110=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 110, 0, input);

                        throw nvae;
                    }
                    switch (alt110) {
                        case 1 :
                            // InternalSqlParser.g:6085:2: otherlv_6= PRECEDING
                            {
                            otherlv_6=(Token)match(input,PRECEDING,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                  	newLeafNode(otherlv_6, grammarAccess.getWindowingClauseOperandFollowingAccess().getPRECEDINGKeyword_1_2_1_0());
                                  
                            }

                            }
                            break;
                        case 2 :
                            // InternalSqlParser.g:6091:2: otherlv_7= KW_FOLLOWING
                            {
                            otherlv_7=(Token)match(input,KW_FOLLOWING,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                  	newLeafNode(otherlv_7, grammarAccess.getWindowingClauseOperandFollowingAccess().getFOLLOWINGKeyword_1_2_1_1());
                                  
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleWindowingClauseOperandFollowing"


    // $ANTLR start "entryRuleWindowingClauseOperandPreceding"
    // InternalSqlParser.g:6103:1: entryRuleWindowingClauseOperandPreceding returns [EObject current=null] : iv_ruleWindowingClauseOperandPreceding= ruleWindowingClauseOperandPreceding EOF ;
    public final EObject entryRuleWindowingClauseOperandPreceding() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWindowingClauseOperandPreceding = null;


        try {
            // InternalSqlParser.g:6104:2: (iv_ruleWindowingClauseOperandPreceding= ruleWindowingClauseOperandPreceding EOF )
            // InternalSqlParser.g:6105:2: iv_ruleWindowingClauseOperandPreceding= ruleWindowingClauseOperandPreceding EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getWindowingClauseOperandPrecedingRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleWindowingClauseOperandPreceding=ruleWindowingClauseOperandPreceding();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleWindowingClauseOperandPreceding; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleWindowingClauseOperandPreceding"


    // $ANTLR start "ruleWindowingClauseOperandPreceding"
    // InternalSqlParser.g:6112:1: ruleWindowingClauseOperandPreceding returns [EObject current=null] : ( () ( (otherlv_1= UNBOUNDED otherlv_2= PRECEDING ) | (otherlv_3= CURRENT otherlv_4= ROW ) | ( ( (lv_expr_5_0= ruleAnalyticExprArg ) ) (otherlv_6= PRECEDING | otherlv_7= KW_FOLLOWING ) ) ) ) ;
    public final EObject ruleWindowingClauseOperandPreceding() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        EObject lv_expr_5_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:6115:28: ( ( () ( (otherlv_1= UNBOUNDED otherlv_2= PRECEDING ) | (otherlv_3= CURRENT otherlv_4= ROW ) | ( ( (lv_expr_5_0= ruleAnalyticExprArg ) ) (otherlv_6= PRECEDING | otherlv_7= KW_FOLLOWING ) ) ) ) )
            // InternalSqlParser.g:6116:1: ( () ( (otherlv_1= UNBOUNDED otherlv_2= PRECEDING ) | (otherlv_3= CURRENT otherlv_4= ROW ) | ( ( (lv_expr_5_0= ruleAnalyticExprArg ) ) (otherlv_6= PRECEDING | otherlv_7= KW_FOLLOWING ) ) ) )
            {
            // InternalSqlParser.g:6116:1: ( () ( (otherlv_1= UNBOUNDED otherlv_2= PRECEDING ) | (otherlv_3= CURRENT otherlv_4= ROW ) | ( ( (lv_expr_5_0= ruleAnalyticExprArg ) ) (otherlv_6= PRECEDING | otherlv_7= KW_FOLLOWING ) ) ) )
            // InternalSqlParser.g:6116:2: () ( (otherlv_1= UNBOUNDED otherlv_2= PRECEDING ) | (otherlv_3= CURRENT otherlv_4= ROW ) | ( ( (lv_expr_5_0= ruleAnalyticExprArg ) ) (otherlv_6= PRECEDING | otherlv_7= KW_FOLLOWING ) ) )
            {
            // InternalSqlParser.g:6116:2: ()
            // InternalSqlParser.g:6117:2: 
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getWindowingClauseOperandPrecedingAccess().getWindowingClauseOperandPrecedingAction_0(),
                          current);
                  
            }

            }

            // InternalSqlParser.g:6125:2: ( (otherlv_1= UNBOUNDED otherlv_2= PRECEDING ) | (otherlv_3= CURRENT otherlv_4= ROW ) | ( ( (lv_expr_5_0= ruleAnalyticExprArg ) ) (otherlv_6= PRECEDING | otherlv_7= KW_FOLLOWING ) ) )
            int alt113=3;
            switch ( input.LA(1) ) {
            case UNBOUNDED:
                {
                alt113=1;
                }
                break;
            case CURRENT:
                {
                alt113=2;
                }
                break;
            case EXTRACT:
            case CAST:
            case CASE:
            case LeftParenthesis:
            case RULE_JRPARAM:
            case RULE_JRNPARAM:
            case RULE_UNSIGNED:
            case RULE_INT:
            case RULE_SIGNED_DOUBLE:
            case RULE_STRING_:
            case RULE_STRING:
            case RULE_DBNAME:
            case RULE_ID:
                {
                alt113=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 113, 0, input);

                throw nvae;
            }

            switch (alt113) {
                case 1 :
                    // InternalSqlParser.g:6125:3: (otherlv_1= UNBOUNDED otherlv_2= PRECEDING )
                    {
                    // InternalSqlParser.g:6125:3: (otherlv_1= UNBOUNDED otherlv_2= PRECEDING )
                    // InternalSqlParser.g:6126:2: otherlv_1= UNBOUNDED otherlv_2= PRECEDING
                    {
                    otherlv_1=(Token)match(input,UNBOUNDED,FOLLOW_86); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_1, grammarAccess.getWindowingClauseOperandPrecedingAccess().getUNBOUNDEDKeyword_1_0_0());
                          
                    }
                    otherlv_2=(Token)match(input,PRECEDING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_2, grammarAccess.getWindowingClauseOperandPrecedingAccess().getPRECEDINGKeyword_1_0_1());
                          
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:6136:6: (otherlv_3= CURRENT otherlv_4= ROW )
                    {
                    // InternalSqlParser.g:6136:6: (otherlv_3= CURRENT otherlv_4= ROW )
                    // InternalSqlParser.g:6137:2: otherlv_3= CURRENT otherlv_4= ROW
                    {
                    otherlv_3=(Token)match(input,CURRENT,FOLLOW_84); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_3, grammarAccess.getWindowingClauseOperandPrecedingAccess().getCURRENTKeyword_1_1_0());
                          
                    }
                    otherlv_4=(Token)match(input,ROW,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_4, grammarAccess.getWindowingClauseOperandPrecedingAccess().getROWKeyword_1_1_1());
                          
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSqlParser.g:6147:6: ( ( (lv_expr_5_0= ruleAnalyticExprArg ) ) (otherlv_6= PRECEDING | otherlv_7= KW_FOLLOWING ) )
                    {
                    // InternalSqlParser.g:6147:6: ( ( (lv_expr_5_0= ruleAnalyticExprArg ) ) (otherlv_6= PRECEDING | otherlv_7= KW_FOLLOWING ) )
                    // InternalSqlParser.g:6147:7: ( (lv_expr_5_0= ruleAnalyticExprArg ) ) (otherlv_6= PRECEDING | otherlv_7= KW_FOLLOWING )
                    {
                    // InternalSqlParser.g:6147:7: ( (lv_expr_5_0= ruleAnalyticExprArg ) )
                    // InternalSqlParser.g:6148:1: (lv_expr_5_0= ruleAnalyticExprArg )
                    {
                    // InternalSqlParser.g:6148:1: (lv_expr_5_0= ruleAnalyticExprArg )
                    // InternalSqlParser.g:6149:3: lv_expr_5_0= ruleAnalyticExprArg
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getWindowingClauseOperandPrecedingAccess().getExprAnalyticExprArgParserRuleCall_1_2_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_85);
                    lv_expr_5_0=ruleAnalyticExprArg();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getWindowingClauseOperandPrecedingRule());
                      	        }
                             		set(
                             			current, 
                             			"expr",
                              		lv_expr_5_0, 
                              		"com.jaspersoft.studio.data.Sql.AnalyticExprArg");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }

                    // InternalSqlParser.g:6165:2: (otherlv_6= PRECEDING | otherlv_7= KW_FOLLOWING )
                    int alt112=2;
                    int LA112_0 = input.LA(1);

                    if ( (LA112_0==PRECEDING) ) {
                        alt112=1;
                    }
                    else if ( (LA112_0==KW_FOLLOWING) ) {
                        alt112=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 112, 0, input);

                        throw nvae;
                    }
                    switch (alt112) {
                        case 1 :
                            // InternalSqlParser.g:6166:2: otherlv_6= PRECEDING
                            {
                            otherlv_6=(Token)match(input,PRECEDING,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                  	newLeafNode(otherlv_6, grammarAccess.getWindowingClauseOperandPrecedingAccess().getPRECEDINGKeyword_1_2_1_0());
                                  
                            }

                            }
                            break;
                        case 2 :
                            // InternalSqlParser.g:6172:2: otherlv_7= KW_FOLLOWING
                            {
                            otherlv_7=(Token)match(input,KW_FOLLOWING,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                  	newLeafNode(otherlv_7, grammarAccess.getWindowingClauseOperandPrecedingAccess().getFOLLOWINGKeyword_1_2_1_1());
                                  
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleWindowingClauseOperandPreceding"


    // $ANTLR start "entryRuleOrderByClause"
    // InternalSqlParser.g:6184:1: entryRuleOrderByClause returns [EObject current=null] : iv_ruleOrderByClause= ruleOrderByClause EOF ;
    public final EObject entryRuleOrderByClause() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrderByClause = null;


        try {
            // InternalSqlParser.g:6185:2: (iv_ruleOrderByClause= ruleOrderByClause EOF )
            // InternalSqlParser.g:6186:2: iv_ruleOrderByClause= ruleOrderByClause EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOrderByClauseRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOrderByClause=ruleOrderByClause();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOrderByClause; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOrderByClause"


    // $ANTLR start "ruleOrderByClause"
    // InternalSqlParser.g:6193:1: ruleOrderByClause returns [EObject current=null] : ( ( (otherlv_0= ORDER otherlv_1= BY ) | (otherlv_2= ORDER otherlv_3= SIBLINGS otherlv_4= BY ) ) ( (lv_args_5_0= ruleOrderByClauseArgs ) ) ) ;
    public final EObject ruleOrderByClause() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        EObject lv_args_5_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:6196:28: ( ( ( (otherlv_0= ORDER otherlv_1= BY ) | (otherlv_2= ORDER otherlv_3= SIBLINGS otherlv_4= BY ) ) ( (lv_args_5_0= ruleOrderByClauseArgs ) ) ) )
            // InternalSqlParser.g:6197:1: ( ( (otherlv_0= ORDER otherlv_1= BY ) | (otherlv_2= ORDER otherlv_3= SIBLINGS otherlv_4= BY ) ) ( (lv_args_5_0= ruleOrderByClauseArgs ) ) )
            {
            // InternalSqlParser.g:6197:1: ( ( (otherlv_0= ORDER otherlv_1= BY ) | (otherlv_2= ORDER otherlv_3= SIBLINGS otherlv_4= BY ) ) ( (lv_args_5_0= ruleOrderByClauseArgs ) ) )
            // InternalSqlParser.g:6197:2: ( (otherlv_0= ORDER otherlv_1= BY ) | (otherlv_2= ORDER otherlv_3= SIBLINGS otherlv_4= BY ) ) ( (lv_args_5_0= ruleOrderByClauseArgs ) )
            {
            // InternalSqlParser.g:6197:2: ( (otherlv_0= ORDER otherlv_1= BY ) | (otherlv_2= ORDER otherlv_3= SIBLINGS otherlv_4= BY ) )
            int alt114=2;
            int LA114_0 = input.LA(1);

            if ( (LA114_0==ORDER) ) {
                int LA114_1 = input.LA(2);

                if ( (LA114_1==SIBLINGS) ) {
                    alt114=2;
                }
                else if ( (LA114_1==BY) ) {
                    alt114=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 114, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 114, 0, input);

                throw nvae;
            }
            switch (alt114) {
                case 1 :
                    // InternalSqlParser.g:6197:3: (otherlv_0= ORDER otherlv_1= BY )
                    {
                    // InternalSqlParser.g:6197:3: (otherlv_0= ORDER otherlv_1= BY )
                    // InternalSqlParser.g:6198:2: otherlv_0= ORDER otherlv_1= BY
                    {
                    otherlv_0=(Token)match(input,ORDER,FOLLOW_25); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_0, grammarAccess.getOrderByClauseAccess().getORDERKeyword_0_0_0());
                          
                    }
                    otherlv_1=(Token)match(input,BY,FOLLOW_82); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_1, grammarAccess.getOrderByClauseAccess().getBYKeyword_0_0_1());
                          
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:6208:6: (otherlv_2= ORDER otherlv_3= SIBLINGS otherlv_4= BY )
                    {
                    // InternalSqlParser.g:6208:6: (otherlv_2= ORDER otherlv_3= SIBLINGS otherlv_4= BY )
                    // InternalSqlParser.g:6209:2: otherlv_2= ORDER otherlv_3= SIBLINGS otherlv_4= BY
                    {
                    otherlv_2=(Token)match(input,ORDER,FOLLOW_87); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_2, grammarAccess.getOrderByClauseAccess().getORDERKeyword_0_1_0());
                          
                    }
                    otherlv_3=(Token)match(input,SIBLINGS,FOLLOW_25); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_3, grammarAccess.getOrderByClauseAccess().getSIBLINGSKeyword_0_1_1());
                          
                    }
                    otherlv_4=(Token)match(input,BY,FOLLOW_82); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_4, grammarAccess.getOrderByClauseAccess().getBYKeyword_0_1_2());
                          
                    }

                    }


                    }
                    break;

            }

            // InternalSqlParser.g:6223:3: ( (lv_args_5_0= ruleOrderByClauseArgs ) )
            // InternalSqlParser.g:6224:1: (lv_args_5_0= ruleOrderByClauseArgs )
            {
            // InternalSqlParser.g:6224:1: (lv_args_5_0= ruleOrderByClauseArgs )
            // InternalSqlParser.g:6225:3: lv_args_5_0= ruleOrderByClauseArgs
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getOrderByClauseAccess().getArgsOrderByClauseArgsParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_2);
            lv_args_5_0=ruleOrderByClauseArgs();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getOrderByClauseRule());
              	        }
                     		set(
                     			current, 
                     			"args",
                      		lv_args_5_0, 
                      		"com.jaspersoft.studio.data.Sql.OrderByClauseArgs");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOrderByClause"


    // $ANTLR start "entryRuleOrderByClauseArgs"
    // InternalSqlParser.g:6249:1: entryRuleOrderByClauseArgs returns [EObject current=null] : iv_ruleOrderByClauseArgs= ruleOrderByClauseArgs EOF ;
    public final EObject entryRuleOrderByClauseArgs() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrderByClauseArgs = null;


        try {
            // InternalSqlParser.g:6250:2: (iv_ruleOrderByClauseArgs= ruleOrderByClauseArgs EOF )
            // InternalSqlParser.g:6251:2: iv_ruleOrderByClauseArgs= ruleOrderByClauseArgs EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOrderByClauseArgsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOrderByClauseArgs=ruleOrderByClauseArgs();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOrderByClauseArgs; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOrderByClauseArgs"


    // $ANTLR start "ruleOrderByClauseArgs"
    // InternalSqlParser.g:6258:1: ruleOrderByClauseArgs returns [EObject current=null] : (this_OrderByClauseArg_0= ruleOrderByClauseArg ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleOrderByClauseArg ) ) )+ )? ) ;
    public final EObject ruleOrderByClauseArgs() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_OrderByClauseArg_0 = null;

        EObject lv_entries_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:6261:28: ( (this_OrderByClauseArg_0= ruleOrderByClauseArg ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleOrderByClauseArg ) ) )+ )? ) )
            // InternalSqlParser.g:6262:1: (this_OrderByClauseArg_0= ruleOrderByClauseArg ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleOrderByClauseArg ) ) )+ )? )
            {
            // InternalSqlParser.g:6262:1: (this_OrderByClauseArg_0= ruleOrderByClauseArg ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleOrderByClauseArg ) ) )+ )? )
            // InternalSqlParser.g:6263:2: this_OrderByClauseArg_0= ruleOrderByClauseArg ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleOrderByClauseArg ) ) )+ )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getOrderByClauseArgsAccess().getOrderByClauseArgParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_9);
            this_OrderByClauseArg_0=ruleOrderByClauseArg();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_OrderByClauseArg_0;
                      afterParserOrEnumRuleCall();
                  
            }
            // InternalSqlParser.g:6274:1: ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleOrderByClauseArg ) ) )+ )?
            int alt116=2;
            int LA116_0 = input.LA(1);

            if ( (LA116_0==Comma) ) {
                alt116=1;
            }
            switch (alt116) {
                case 1 :
                    // InternalSqlParser.g:6274:2: () (otherlv_2= Comma ( (lv_entries_3_0= ruleOrderByClauseArg ) ) )+
                    {
                    // InternalSqlParser.g:6274:2: ()
                    // InternalSqlParser.g:6275:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getOrderByClauseArgsAccess().getOBCArgsEntriesAction_1_0(),
                                  current);
                          
                    }

                    }

                    // InternalSqlParser.g:6283:2: (otherlv_2= Comma ( (lv_entries_3_0= ruleOrderByClauseArg ) ) )+
                    int cnt115=0;
                    loop115:
                    do {
                        int alt115=2;
                        int LA115_0 = input.LA(1);

                        if ( (LA115_0==Comma) ) {
                            alt115=1;
                        }


                        switch (alt115) {
                    	case 1 :
                    	    // InternalSqlParser.g:6284:2: otherlv_2= Comma ( (lv_entries_3_0= ruleOrderByClauseArg ) )
                    	    {
                    	    otherlv_2=(Token)match(input,Comma,FOLLOW_82); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_2, grammarAccess.getOrderByClauseArgsAccess().getCommaKeyword_1_1_0());
                    	          
                    	    }
                    	    // InternalSqlParser.g:6288:1: ( (lv_entries_3_0= ruleOrderByClauseArg ) )
                    	    // InternalSqlParser.g:6289:1: (lv_entries_3_0= ruleOrderByClauseArg )
                    	    {
                    	    // InternalSqlParser.g:6289:1: (lv_entries_3_0= ruleOrderByClauseArg )
                    	    // InternalSqlParser.g:6290:3: lv_entries_3_0= ruleOrderByClauseArg
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getOrderByClauseArgsAccess().getEntriesOrderByClauseArgParserRuleCall_1_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_9);
                    	    lv_entries_3_0=ruleOrderByClauseArg();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getOrderByClauseArgsRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"entries",
                    	              		lv_entries_3_0, 
                    	              		"com.jaspersoft.studio.data.Sql.OrderByClauseArg");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt115 >= 1 ) break loop115;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(115, input);
                                throw eee;
                        }
                        cnt115++;
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOrderByClauseArgs"


    // $ANTLR start "entryRuleOrderByClauseArg"
    // InternalSqlParser.g:6314:1: entryRuleOrderByClauseArg returns [EObject current=null] : iv_ruleOrderByClauseArg= ruleOrderByClauseArg EOF ;
    public final EObject entryRuleOrderByClauseArg() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrderByClauseArg = null;


        try {
            // InternalSqlParser.g:6315:2: (iv_ruleOrderByClauseArg= ruleOrderByClauseArg EOF )
            // InternalSqlParser.g:6316:2: iv_ruleOrderByClauseArg= ruleOrderByClauseArg EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOrderByClauseArgRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOrderByClauseArg=ruleOrderByClauseArg();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOrderByClauseArg; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOrderByClauseArg"


    // $ANTLR start "ruleOrderByClauseArg"
    // InternalSqlParser.g:6323:1: ruleOrderByClauseArg returns [EObject current=null] : ( ( (lv_col_0_0= ruleAnalyticExprArg ) ) (otherlv_1= ASC | otherlv_2= DESC )? (otherlv_3= NULLS (otherlv_4= FIRST | otherlv_5= LAST ) )? ) ;
    public final EObject ruleOrderByClauseArg() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        EObject lv_col_0_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:6326:28: ( ( ( (lv_col_0_0= ruleAnalyticExprArg ) ) (otherlv_1= ASC | otherlv_2= DESC )? (otherlv_3= NULLS (otherlv_4= FIRST | otherlv_5= LAST ) )? ) )
            // InternalSqlParser.g:6327:1: ( ( (lv_col_0_0= ruleAnalyticExprArg ) ) (otherlv_1= ASC | otherlv_2= DESC )? (otherlv_3= NULLS (otherlv_4= FIRST | otherlv_5= LAST ) )? )
            {
            // InternalSqlParser.g:6327:1: ( ( (lv_col_0_0= ruleAnalyticExprArg ) ) (otherlv_1= ASC | otherlv_2= DESC )? (otherlv_3= NULLS (otherlv_4= FIRST | otherlv_5= LAST ) )? )
            // InternalSqlParser.g:6327:2: ( (lv_col_0_0= ruleAnalyticExprArg ) ) (otherlv_1= ASC | otherlv_2= DESC )? (otherlv_3= NULLS (otherlv_4= FIRST | otherlv_5= LAST ) )?
            {
            // InternalSqlParser.g:6327:2: ( (lv_col_0_0= ruleAnalyticExprArg ) )
            // InternalSqlParser.g:6328:1: (lv_col_0_0= ruleAnalyticExprArg )
            {
            // InternalSqlParser.g:6328:1: (lv_col_0_0= ruleAnalyticExprArg )
            // InternalSqlParser.g:6329:3: lv_col_0_0= ruleAnalyticExprArg
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getOrderByClauseArgAccess().getColAnalyticExprArgParserRuleCall_0_0()); 
              	    
            }
            pushFollow(FOLLOW_88);
            lv_col_0_0=ruleAnalyticExprArg();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getOrderByClauseArgRule());
              	        }
                     		set(
                     			current, 
                     			"col",
                      		lv_col_0_0, 
                      		"com.jaspersoft.studio.data.Sql.AnalyticExprArg");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:6345:2: (otherlv_1= ASC | otherlv_2= DESC )?
            int alt117=3;
            int LA117_0 = input.LA(1);

            if ( (LA117_0==ASC) ) {
                alt117=1;
            }
            else if ( (LA117_0==DESC) ) {
                alt117=2;
            }
            switch (alt117) {
                case 1 :
                    // InternalSqlParser.g:6346:2: otherlv_1= ASC
                    {
                    otherlv_1=(Token)match(input,ASC,FOLLOW_89); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_1, grammarAccess.getOrderByClauseArgAccess().getASCKeyword_1_0());
                          
                    }

                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:6352:2: otherlv_2= DESC
                    {
                    otherlv_2=(Token)match(input,DESC,FOLLOW_89); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_2, grammarAccess.getOrderByClauseArgAccess().getDESCKeyword_1_1());
                          
                    }

                    }
                    break;

            }

            // InternalSqlParser.g:6356:3: (otherlv_3= NULLS (otherlv_4= FIRST | otherlv_5= LAST ) )?
            int alt119=2;
            int LA119_0 = input.LA(1);

            if ( (LA119_0==NULLS) ) {
                alt119=1;
            }
            switch (alt119) {
                case 1 :
                    // InternalSqlParser.g:6357:2: otherlv_3= NULLS (otherlv_4= FIRST | otherlv_5= LAST )
                    {
                    otherlv_3=(Token)match(input,NULLS,FOLLOW_90); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_3, grammarAccess.getOrderByClauseArgAccess().getNULLSKeyword_2_0());
                          
                    }
                    // InternalSqlParser.g:6361:1: (otherlv_4= FIRST | otherlv_5= LAST )
                    int alt118=2;
                    int LA118_0 = input.LA(1);

                    if ( (LA118_0==FIRST) ) {
                        alt118=1;
                    }
                    else if ( (LA118_0==LAST) ) {
                        alt118=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 118, 0, input);

                        throw nvae;
                    }
                    switch (alt118) {
                        case 1 :
                            // InternalSqlParser.g:6362:2: otherlv_4= FIRST
                            {
                            otherlv_4=(Token)match(input,FIRST,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                  	newLeafNode(otherlv_4, grammarAccess.getOrderByClauseArgAccess().getFIRSTKeyword_2_1_0());
                                  
                            }

                            }
                            break;
                        case 2 :
                            // InternalSqlParser.g:6368:2: otherlv_5= LAST
                            {
                            otherlv_5=(Token)match(input,LAST,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                  	newLeafNode(otherlv_5, grammarAccess.getOrderByClauseArgAccess().getLASTKeyword_2_1_1());
                                  
                            }

                            }
                            break;

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOrderByClauseArg"


    // $ANTLR start "entryRuleQueryPartitionClause"
    // InternalSqlParser.g:6380:1: entryRuleQueryPartitionClause returns [EObject current=null] : iv_ruleQueryPartitionClause= ruleQueryPartitionClause EOF ;
    public final EObject entryRuleQueryPartitionClause() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQueryPartitionClause = null;


        try {
            // InternalSqlParser.g:6381:2: (iv_ruleQueryPartitionClause= ruleQueryPartitionClause EOF )
            // InternalSqlParser.g:6382:2: iv_ruleQueryPartitionClause= ruleQueryPartitionClause EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getQueryPartitionClauseRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleQueryPartitionClause=ruleQueryPartitionClause();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleQueryPartitionClause; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleQueryPartitionClause"


    // $ANTLR start "ruleQueryPartitionClause"
    // InternalSqlParser.g:6389:1: ruleQueryPartitionClause returns [EObject current=null] : (otherlv_0= PARTITION otherlv_1= BY ( ( (lv_args_2_0= ruleAnalyticExprArgs ) ) | (otherlv_3= LeftParenthesis this_AnalyticExprArgs_4= ruleAnalyticExprArgs otherlv_5= RightParenthesis ) ) ) ;
    public final EObject ruleQueryPartitionClause() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_args_2_0 = null;

        EObject this_AnalyticExprArgs_4 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:6392:28: ( (otherlv_0= PARTITION otherlv_1= BY ( ( (lv_args_2_0= ruleAnalyticExprArgs ) ) | (otherlv_3= LeftParenthesis this_AnalyticExprArgs_4= ruleAnalyticExprArgs otherlv_5= RightParenthesis ) ) ) )
            // InternalSqlParser.g:6393:1: (otherlv_0= PARTITION otherlv_1= BY ( ( (lv_args_2_0= ruleAnalyticExprArgs ) ) | (otherlv_3= LeftParenthesis this_AnalyticExprArgs_4= ruleAnalyticExprArgs otherlv_5= RightParenthesis ) ) )
            {
            // InternalSqlParser.g:6393:1: (otherlv_0= PARTITION otherlv_1= BY ( ( (lv_args_2_0= ruleAnalyticExprArgs ) ) | (otherlv_3= LeftParenthesis this_AnalyticExprArgs_4= ruleAnalyticExprArgs otherlv_5= RightParenthesis ) ) )
            // InternalSqlParser.g:6394:2: otherlv_0= PARTITION otherlv_1= BY ( ( (lv_args_2_0= ruleAnalyticExprArgs ) ) | (otherlv_3= LeftParenthesis this_AnalyticExprArgs_4= ruleAnalyticExprArgs otherlv_5= RightParenthesis ) )
            {
            otherlv_0=(Token)match(input,PARTITION,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getQueryPartitionClauseAccess().getPARTITIONKeyword_0());
                  
            }
            otherlv_1=(Token)match(input,BY,FOLLOW_82); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getQueryPartitionClauseAccess().getBYKeyword_1());
                  
            }
            // InternalSqlParser.g:6403:1: ( ( (lv_args_2_0= ruleAnalyticExprArgs ) ) | (otherlv_3= LeftParenthesis this_AnalyticExprArgs_4= ruleAnalyticExprArgs otherlv_5= RightParenthesis ) )
            int alt120=2;
            int LA120_0 = input.LA(1);

            if ( (LA120_0==EXTRACT||LA120_0==CAST||LA120_0==CASE||(LA120_0>=RULE_JRPARAM && LA120_0<=RULE_JRNPARAM)||(LA120_0>=RULE_UNSIGNED && LA120_0<=RULE_SIGNED_DOUBLE)||(LA120_0>=RULE_STRING_ && LA120_0<=RULE_ID)) ) {
                alt120=1;
            }
            else if ( (LA120_0==LeftParenthesis) ) {
                int LA120_2 = input.LA(2);

                if ( (LA120_2==EXTRACT||LA120_2==CAST||LA120_2==CASE||LA120_2==LeftParenthesis||(LA120_2>=RULE_JRPARAM && LA120_2<=RULE_JRNPARAM)||(LA120_2>=RULE_UNSIGNED && LA120_2<=RULE_SIGNED_DOUBLE)||(LA120_2>=RULE_STRING_ && LA120_2<=RULE_ID)) ) {
                    alt120=2;
                }
                else if ( (LA120_2==SELECT) ) {
                    alt120=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 120, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 120, 0, input);

                throw nvae;
            }
            switch (alt120) {
                case 1 :
                    // InternalSqlParser.g:6403:2: ( (lv_args_2_0= ruleAnalyticExprArgs ) )
                    {
                    // InternalSqlParser.g:6403:2: ( (lv_args_2_0= ruleAnalyticExprArgs ) )
                    // InternalSqlParser.g:6404:1: (lv_args_2_0= ruleAnalyticExprArgs )
                    {
                    // InternalSqlParser.g:6404:1: (lv_args_2_0= ruleAnalyticExprArgs )
                    // InternalSqlParser.g:6405:3: lv_args_2_0= ruleAnalyticExprArgs
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getQueryPartitionClauseAccess().getArgsAnalyticExprArgsParserRuleCall_2_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_args_2_0=ruleAnalyticExprArgs();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getQueryPartitionClauseRule());
                      	        }
                             		set(
                             			current, 
                             			"args",
                              		lv_args_2_0, 
                              		"com.jaspersoft.studio.data.Sql.AnalyticExprArgs");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:6422:6: (otherlv_3= LeftParenthesis this_AnalyticExprArgs_4= ruleAnalyticExprArgs otherlv_5= RightParenthesis )
                    {
                    // InternalSqlParser.g:6422:6: (otherlv_3= LeftParenthesis this_AnalyticExprArgs_4= ruleAnalyticExprArgs otherlv_5= RightParenthesis )
                    // InternalSqlParser.g:6423:2: otherlv_3= LeftParenthesis this_AnalyticExprArgs_4= ruleAnalyticExprArgs otherlv_5= RightParenthesis
                    {
                    otherlv_3=(Token)match(input,LeftParenthesis,FOLLOW_82); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_3, grammarAccess.getQueryPartitionClauseAccess().getLeftParenthesisKeyword_2_1_0());
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getQueryPartitionClauseAccess().getAnalyticExprArgsParserRuleCall_2_1_1()); 
                          
                    }
                    pushFollow(FOLLOW_8);
                    this_AnalyticExprArgs_4=ruleAnalyticExprArgs();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_AnalyticExprArgs_4;
                              afterParserOrEnumRuleCall();
                          
                    }
                    otherlv_5=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_5, grammarAccess.getQueryPartitionClauseAccess().getRightParenthesisKeyword_2_1_2());
                          
                    }

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleQueryPartitionClause"


    // $ANTLR start "entryRuleAnalyticExprArgs"
    // InternalSqlParser.g:6452:1: entryRuleAnalyticExprArgs returns [EObject current=null] : iv_ruleAnalyticExprArgs= ruleAnalyticExprArgs EOF ;
    public final EObject entryRuleAnalyticExprArgs() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnalyticExprArgs = null;


        try {
            // InternalSqlParser.g:6453:2: (iv_ruleAnalyticExprArgs= ruleAnalyticExprArgs EOF )
            // InternalSqlParser.g:6454:2: iv_ruleAnalyticExprArgs= ruleAnalyticExprArgs EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAnalyticExprArgsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAnalyticExprArgs=ruleAnalyticExprArgs();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAnalyticExprArgs; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAnalyticExprArgs"


    // $ANTLR start "ruleAnalyticExprArgs"
    // InternalSqlParser.g:6461:1: ruleAnalyticExprArgs returns [EObject current=null] : (this_AnalyticExprArg_0= ruleAnalyticExprArg ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleAnalyticExprArg ) ) )+ )? ) ;
    public final EObject ruleAnalyticExprArgs() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_AnalyticExprArg_0 = null;

        EObject lv_entries_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:6464:28: ( (this_AnalyticExprArg_0= ruleAnalyticExprArg ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleAnalyticExprArg ) ) )+ )? ) )
            // InternalSqlParser.g:6465:1: (this_AnalyticExprArg_0= ruleAnalyticExprArg ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleAnalyticExprArg ) ) )+ )? )
            {
            // InternalSqlParser.g:6465:1: (this_AnalyticExprArg_0= ruleAnalyticExprArg ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleAnalyticExprArg ) ) )+ )? )
            // InternalSqlParser.g:6466:2: this_AnalyticExprArg_0= ruleAnalyticExprArg ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleAnalyticExprArg ) ) )+ )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getAnalyticExprArgsAccess().getAnalyticExprArgParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_9);
            this_AnalyticExprArg_0=ruleAnalyticExprArg();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_AnalyticExprArg_0;
                      afterParserOrEnumRuleCall();
                  
            }
            // InternalSqlParser.g:6477:1: ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleAnalyticExprArg ) ) )+ )?
            int alt122=2;
            int LA122_0 = input.LA(1);

            if ( (LA122_0==Comma) ) {
                alt122=1;
            }
            switch (alt122) {
                case 1 :
                    // InternalSqlParser.g:6477:2: () (otherlv_2= Comma ( (lv_entries_3_0= ruleAnalyticExprArg ) ) )+
                    {
                    // InternalSqlParser.g:6477:2: ()
                    // InternalSqlParser.g:6478:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getAnalyticExprArgsAccess().getAExpArgsEntriesAction_1_0(),
                                  current);
                          
                    }

                    }

                    // InternalSqlParser.g:6486:2: (otherlv_2= Comma ( (lv_entries_3_0= ruleAnalyticExprArg ) ) )+
                    int cnt121=0;
                    loop121:
                    do {
                        int alt121=2;
                        int LA121_0 = input.LA(1);

                        if ( (LA121_0==Comma) ) {
                            alt121=1;
                        }


                        switch (alt121) {
                    	case 1 :
                    	    // InternalSqlParser.g:6487:2: otherlv_2= Comma ( (lv_entries_3_0= ruleAnalyticExprArg ) )
                    	    {
                    	    otherlv_2=(Token)match(input,Comma,FOLLOW_82); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_2, grammarAccess.getAnalyticExprArgsAccess().getCommaKeyword_1_1_0());
                    	          
                    	    }
                    	    // InternalSqlParser.g:6491:1: ( (lv_entries_3_0= ruleAnalyticExprArg ) )
                    	    // InternalSqlParser.g:6492:1: (lv_entries_3_0= ruleAnalyticExprArg )
                    	    {
                    	    // InternalSqlParser.g:6492:1: (lv_entries_3_0= ruleAnalyticExprArg )
                    	    // InternalSqlParser.g:6493:3: lv_entries_3_0= ruleAnalyticExprArg
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getAnalyticExprArgsAccess().getEntriesAnalyticExprArgParserRuleCall_1_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_9);
                    	    lv_entries_3_0=ruleAnalyticExprArg();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getAnalyticExprArgsRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"entries",
                    	              		lv_entries_3_0, 
                    	              		"com.jaspersoft.studio.data.Sql.AnalyticExprArg");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt121 >= 1 ) break loop121;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(121, input);
                                throw eee;
                        }
                        cnt121++;
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAnalyticExprArgs"


    // $ANTLR start "entryRuleAnalyticExprArg"
    // InternalSqlParser.g:6517:1: entryRuleAnalyticExprArg returns [EObject current=null] : iv_ruleAnalyticExprArg= ruleAnalyticExprArg EOF ;
    public final EObject entryRuleAnalyticExprArg() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnalyticExprArg = null;


        try {
            // InternalSqlParser.g:6518:2: (iv_ruleAnalyticExprArg= ruleAnalyticExprArg EOF )
            // InternalSqlParser.g:6519:2: iv_ruleAnalyticExprArg= ruleAnalyticExprArg EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAnalyticExprArgRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAnalyticExprArg=ruleAnalyticExprArg();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAnalyticExprArg; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAnalyticExprArg"


    // $ANTLR start "ruleAnalyticExprArg"
    // InternalSqlParser.g:6526:1: ruleAnalyticExprArg returns [EObject current=null] : ( ( (lv_ce_0_0= ruleOperand ) ) ( (lv_colAlias_1_0= ruleDbObjectName ) )? ) ;
    public final EObject ruleAnalyticExprArg() throws RecognitionException {
        EObject current = null;

        EObject lv_ce_0_0 = null;

        EObject lv_colAlias_1_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:6529:28: ( ( ( (lv_ce_0_0= ruleOperand ) ) ( (lv_colAlias_1_0= ruleDbObjectName ) )? ) )
            // InternalSqlParser.g:6530:1: ( ( (lv_ce_0_0= ruleOperand ) ) ( (lv_colAlias_1_0= ruleDbObjectName ) )? )
            {
            // InternalSqlParser.g:6530:1: ( ( (lv_ce_0_0= ruleOperand ) ) ( (lv_colAlias_1_0= ruleDbObjectName ) )? )
            // InternalSqlParser.g:6530:2: ( (lv_ce_0_0= ruleOperand ) ) ( (lv_colAlias_1_0= ruleDbObjectName ) )?
            {
            // InternalSqlParser.g:6530:2: ( (lv_ce_0_0= ruleOperand ) )
            // InternalSqlParser.g:6531:1: (lv_ce_0_0= ruleOperand )
            {
            // InternalSqlParser.g:6531:1: (lv_ce_0_0= ruleOperand )
            // InternalSqlParser.g:6532:3: lv_ce_0_0= ruleOperand
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getAnalyticExprArgAccess().getCeOperandParserRuleCall_0_0()); 
              	    
            }
            pushFollow(FOLLOW_36);
            lv_ce_0_0=ruleOperand();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getAnalyticExprArgRule());
              	        }
                     		set(
                     			current, 
                     			"ce",
                      		lv_ce_0_0, 
                      		"com.jaspersoft.studio.data.Sql.Operand");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:6548:2: ( (lv_colAlias_1_0= ruleDbObjectName ) )?
            int alt123=2;
            int LA123_0 = input.LA(1);

            if ( ((LA123_0>=RULE_STRING && LA123_0<=RULE_ID)) ) {
                alt123=1;
            }
            switch (alt123) {
                case 1 :
                    // InternalSqlParser.g:6549:1: (lv_colAlias_1_0= ruleDbObjectName )
                    {
                    // InternalSqlParser.g:6549:1: (lv_colAlias_1_0= ruleDbObjectName )
                    // InternalSqlParser.g:6550:3: lv_colAlias_1_0= ruleDbObjectName
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getAnalyticExprArgAccess().getColAliasDbObjectNameParserRuleCall_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_colAlias_1_0=ruleDbObjectName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getAnalyticExprArgRule());
                      	        }
                             		set(
                             			current, 
                             			"colAlias",
                              		lv_colAlias_1_0, 
                              		"com.jaspersoft.studio.data.Sql.DbObjectName");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAnalyticExprArg"


    // $ANTLR start "entryRuleOpFunctionArg"
    // InternalSqlParser.g:6574:1: entryRuleOpFunctionArg returns [EObject current=null] : iv_ruleOpFunctionArg= ruleOpFunctionArg EOF ;
    public final EObject entryRuleOpFunctionArg() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOpFunctionArg = null;


        try {
            // InternalSqlParser.g:6575:2: (iv_ruleOpFunctionArg= ruleOpFunctionArg EOF )
            // InternalSqlParser.g:6576:2: iv_ruleOpFunctionArg= ruleOpFunctionArg EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpFunctionArgRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOpFunctionArg=ruleOpFunctionArg();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpFunctionArg; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOpFunctionArg"


    // $ANTLR start "ruleOpFunctionArg"
    // InternalSqlParser.g:6583:1: ruleOpFunctionArg returns [EObject current=null] : (this_OpFunctionArgOperand_0= ruleOpFunctionArgOperand ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleOpFunctionArgOperand ) ) )+ )? ) ;
    public final EObject ruleOpFunctionArg() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_OpFunctionArgOperand_0 = null;

        EObject lv_entries_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:6586:28: ( (this_OpFunctionArgOperand_0= ruleOpFunctionArgOperand ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleOpFunctionArgOperand ) ) )+ )? ) )
            // InternalSqlParser.g:6587:1: (this_OpFunctionArgOperand_0= ruleOpFunctionArgOperand ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleOpFunctionArgOperand ) ) )+ )? )
            {
            // InternalSqlParser.g:6587:1: (this_OpFunctionArgOperand_0= ruleOpFunctionArgOperand ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleOpFunctionArgOperand ) ) )+ )? )
            // InternalSqlParser.g:6588:2: this_OpFunctionArgOperand_0= ruleOpFunctionArgOperand ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleOpFunctionArgOperand ) ) )+ )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getOpFunctionArgAccess().getOpFunctionArgOperandParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_9);
            this_OpFunctionArgOperand_0=ruleOpFunctionArgOperand();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_OpFunctionArgOperand_0;
                      afterParserOrEnumRuleCall();
                  
            }
            // InternalSqlParser.g:6599:1: ( () (otherlv_2= Comma ( (lv_entries_3_0= ruleOpFunctionArgOperand ) ) )+ )?
            int alt125=2;
            int LA125_0 = input.LA(1);

            if ( (LA125_0==Comma) ) {
                alt125=1;
            }
            switch (alt125) {
                case 1 :
                    // InternalSqlParser.g:6599:2: () (otherlv_2= Comma ( (lv_entries_3_0= ruleOpFunctionArgOperand ) ) )+
                    {
                    // InternalSqlParser.g:6599:2: ()
                    // InternalSqlParser.g:6600:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getOpFunctionArgAccess().getOpFListEntriesAction_1_0(),
                                  current);
                          
                    }

                    }

                    // InternalSqlParser.g:6608:2: (otherlv_2= Comma ( (lv_entries_3_0= ruleOpFunctionArgOperand ) ) )+
                    int cnt124=0;
                    loop124:
                    do {
                        int alt124=2;
                        int LA124_0 = input.LA(1);

                        if ( (LA124_0==Comma) ) {
                            alt124=1;
                        }


                        switch (alt124) {
                    	case 1 :
                    	    // InternalSqlParser.g:6609:2: otherlv_2= Comma ( (lv_entries_3_0= ruleOpFunctionArgOperand ) )
                    	    {
                    	    otherlv_2=(Token)match(input,Comma,FOLLOW_91); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_2, grammarAccess.getOpFunctionArgAccess().getCommaKeyword_1_1_0());
                    	          
                    	    }
                    	    // InternalSqlParser.g:6613:1: ( (lv_entries_3_0= ruleOpFunctionArgOperand ) )
                    	    // InternalSqlParser.g:6614:1: (lv_entries_3_0= ruleOpFunctionArgOperand )
                    	    {
                    	    // InternalSqlParser.g:6614:1: (lv_entries_3_0= ruleOpFunctionArgOperand )
                    	    // InternalSqlParser.g:6615:3: lv_entries_3_0= ruleOpFunctionArgOperand
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getOpFunctionArgAccess().getEntriesOpFunctionArgOperandParserRuleCall_1_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_9);
                    	    lv_entries_3_0=ruleOpFunctionArgOperand();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getOpFunctionArgRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"entries",
                    	              		lv_entries_3_0, 
                    	              		"com.jaspersoft.studio.data.Sql.OpFunctionArgOperand");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt124 >= 1 ) break loop124;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(124, input);
                                throw eee;
                        }
                        cnt124++;
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOpFunctionArg"


    // $ANTLR start "entryRuleOpFunctionArgOperand"
    // InternalSqlParser.g:6639:1: entryRuleOpFunctionArgOperand returns [EObject current=null] : iv_ruleOpFunctionArgOperand= ruleOpFunctionArgOperand EOF ;
    public final EObject entryRuleOpFunctionArgOperand() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOpFunctionArgOperand = null;


        try {
            // InternalSqlParser.g:6640:2: (iv_ruleOpFunctionArgOperand= ruleOpFunctionArgOperand EOF )
            // InternalSqlParser.g:6641:2: iv_ruleOpFunctionArgOperand= ruleOpFunctionArgOperand EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpFunctionArgOperandRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOpFunctionArgOperand=ruleOpFunctionArgOperand();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpFunctionArgOperand; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOpFunctionArgOperand"


    // $ANTLR start "ruleOpFunctionArgOperand"
    // InternalSqlParser.g:6648:1: ruleOpFunctionArgOperand returns [EObject current=null] : ( ( (lv_op_0_1= ruleOpFunctionArgAgregate | lv_op_0_2= ruleOperand ) ) ) ;
    public final EObject ruleOpFunctionArgOperand() throws RecognitionException {
        EObject current = null;

        EObject lv_op_0_1 = null;

        EObject lv_op_0_2 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:6651:28: ( ( ( (lv_op_0_1= ruleOpFunctionArgAgregate | lv_op_0_2= ruleOperand ) ) ) )
            // InternalSqlParser.g:6652:1: ( ( (lv_op_0_1= ruleOpFunctionArgAgregate | lv_op_0_2= ruleOperand ) ) )
            {
            // InternalSqlParser.g:6652:1: ( ( (lv_op_0_1= ruleOpFunctionArgAgregate | lv_op_0_2= ruleOperand ) ) )
            // InternalSqlParser.g:6653:1: ( (lv_op_0_1= ruleOpFunctionArgAgregate | lv_op_0_2= ruleOperand ) )
            {
            // InternalSqlParser.g:6653:1: ( (lv_op_0_1= ruleOpFunctionArgAgregate | lv_op_0_2= ruleOperand ) )
            // InternalSqlParser.g:6654:1: (lv_op_0_1= ruleOpFunctionArgAgregate | lv_op_0_2= ruleOperand )
            {
            // InternalSqlParser.g:6654:1: (lv_op_0_1= ruleOpFunctionArgAgregate | lv_op_0_2= ruleOperand )
            int alt126=2;
            int LA126_0 = input.LA(1);

            if ( (LA126_0==DISTINCT||LA126_0==ALL) ) {
                alt126=1;
            }
            else if ( (LA126_0==EXTRACT||LA126_0==CAST||LA126_0==CASE||LA126_0==LeftParenthesis||(LA126_0>=RULE_JRPARAM && LA126_0<=RULE_JRNPARAM)||(LA126_0>=RULE_UNSIGNED && LA126_0<=RULE_SIGNED_DOUBLE)||(LA126_0>=RULE_STRING_ && LA126_0<=RULE_ID)) ) {
                alt126=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 126, 0, input);

                throw nvae;
            }
            switch (alt126) {
                case 1 :
                    // InternalSqlParser.g:6655:3: lv_op_0_1= ruleOpFunctionArgAgregate
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getOpFunctionArgOperandAccess().getOpOpFunctionArgAgregateParserRuleCall_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_op_0_1=ruleOpFunctionArgAgregate();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getOpFunctionArgOperandRule());
                      	        }
                             		set(
                             			current, 
                             			"op",
                              		lv_op_0_1, 
                              		"com.jaspersoft.studio.data.Sql.OpFunctionArgAgregate");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:6670:8: lv_op_0_2= ruleOperand
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getOpFunctionArgOperandAccess().getOpOperandParserRuleCall_0_1()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_op_0_2=ruleOperand();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getOpFunctionArgOperandRule());
                      	        }
                             		set(
                             			current, 
                             			"op",
                              		lv_op_0_2, 
                              		"com.jaspersoft.studio.data.Sql.Operand");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }
                    break;

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOpFunctionArgOperand"


    // $ANTLR start "entryRuleOpFunctionCast"
    // InternalSqlParser.g:6696:1: entryRuleOpFunctionCast returns [EObject current=null] : iv_ruleOpFunctionCast= ruleOpFunctionCast EOF ;
    public final EObject entryRuleOpFunctionCast() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOpFunctionCast = null;


        try {
            // InternalSqlParser.g:6697:2: (iv_ruleOpFunctionCast= ruleOpFunctionCast EOF )
            // InternalSqlParser.g:6698:2: iv_ruleOpFunctionCast= ruleOpFunctionCast EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpFunctionCastRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOpFunctionCast=ruleOpFunctionCast();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpFunctionCast; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOpFunctionCast"


    // $ANTLR start "ruleOpFunctionCast"
    // InternalSqlParser.g:6705:1: ruleOpFunctionCast returns [EObject current=null] : (otherlv_0= CAST ( (lv_op_1_0= ruleOperandGroup ) ) otherlv_2= AS ( (lv_type_3_0= RULE_ID ) ) (otherlv_4= LeftParenthesis ( (lv_p_5_0= RULE_UNSIGNED ) ) (otherlv_6= Comma ( (lv_p2_7_0= RULE_UNSIGNED ) ) )? otherlv_8= RightParenthesis )? otherlv_9= RightParenthesis ) ;
    public final EObject ruleOpFunctionCast() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token lv_type_3_0=null;
        Token otherlv_4=null;
        Token lv_p_5_0=null;
        Token otherlv_6=null;
        Token lv_p2_7_0=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        EObject lv_op_1_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:6708:28: ( (otherlv_0= CAST ( (lv_op_1_0= ruleOperandGroup ) ) otherlv_2= AS ( (lv_type_3_0= RULE_ID ) ) (otherlv_4= LeftParenthesis ( (lv_p_5_0= RULE_UNSIGNED ) ) (otherlv_6= Comma ( (lv_p2_7_0= RULE_UNSIGNED ) ) )? otherlv_8= RightParenthesis )? otherlv_9= RightParenthesis ) )
            // InternalSqlParser.g:6709:1: (otherlv_0= CAST ( (lv_op_1_0= ruleOperandGroup ) ) otherlv_2= AS ( (lv_type_3_0= RULE_ID ) ) (otherlv_4= LeftParenthesis ( (lv_p_5_0= RULE_UNSIGNED ) ) (otherlv_6= Comma ( (lv_p2_7_0= RULE_UNSIGNED ) ) )? otherlv_8= RightParenthesis )? otherlv_9= RightParenthesis )
            {
            // InternalSqlParser.g:6709:1: (otherlv_0= CAST ( (lv_op_1_0= ruleOperandGroup ) ) otherlv_2= AS ( (lv_type_3_0= RULE_ID ) ) (otherlv_4= LeftParenthesis ( (lv_p_5_0= RULE_UNSIGNED ) ) (otherlv_6= Comma ( (lv_p2_7_0= RULE_UNSIGNED ) ) )? otherlv_8= RightParenthesis )? otherlv_9= RightParenthesis )
            // InternalSqlParser.g:6710:2: otherlv_0= CAST ( (lv_op_1_0= ruleOperandGroup ) ) otherlv_2= AS ( (lv_type_3_0= RULE_ID ) ) (otherlv_4= LeftParenthesis ( (lv_p_5_0= RULE_UNSIGNED ) ) (otherlv_6= Comma ( (lv_p2_7_0= RULE_UNSIGNED ) ) )? otherlv_8= RightParenthesis )? otherlv_9= RightParenthesis
            {
            otherlv_0=(Token)match(input,CAST,FOLLOW_60); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getOpFunctionCastAccess().getCASTKeyword_0());
                  
            }
            // InternalSqlParser.g:6714:1: ( (lv_op_1_0= ruleOperandGroup ) )
            // InternalSqlParser.g:6715:1: (lv_op_1_0= ruleOperandGroup )
            {
            // InternalSqlParser.g:6715:1: (lv_op_1_0= ruleOperandGroup )
            // InternalSqlParser.g:6716:3: lv_op_1_0= ruleOperandGroup
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getOpFunctionCastAccess().getOpOperandGroupParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_6);
            lv_op_1_0=ruleOperandGroup();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getOpFunctionCastRule());
              	        }
                     		set(
                     			current, 
                     			"op",
                      		lv_op_1_0, 
                      		"com.jaspersoft.studio.data.Sql.OperandGroup");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_2=(Token)match(input,AS,FOLLOW_46); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getOpFunctionCastAccess().getASKeyword_2());
                  
            }
            // InternalSqlParser.g:6737:1: ( (lv_type_3_0= RULE_ID ) )
            // InternalSqlParser.g:6738:1: (lv_type_3_0= RULE_ID )
            {
            // InternalSqlParser.g:6738:1: (lv_type_3_0= RULE_ID )
            // InternalSqlParser.g:6739:3: lv_type_3_0= RULE_ID
            {
            lv_type_3_0=(Token)match(input,RULE_ID,FOLLOW_92); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_type_3_0, grammarAccess.getOpFunctionCastAccess().getTypeIDTerminalRuleCall_3_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getOpFunctionCastRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"type",
                      		lv_type_3_0, 
                      		"com.jaspersoft.studio.data.Sql.ID");
              	    
            }

            }


            }

            // InternalSqlParser.g:6755:2: (otherlv_4= LeftParenthesis ( (lv_p_5_0= RULE_UNSIGNED ) ) (otherlv_6= Comma ( (lv_p2_7_0= RULE_UNSIGNED ) ) )? otherlv_8= RightParenthesis )?
            int alt128=2;
            int LA128_0 = input.LA(1);

            if ( (LA128_0==LeftParenthesis) ) {
                alt128=1;
            }
            switch (alt128) {
                case 1 :
                    // InternalSqlParser.g:6756:2: otherlv_4= LeftParenthesis ( (lv_p_5_0= RULE_UNSIGNED ) ) (otherlv_6= Comma ( (lv_p2_7_0= RULE_UNSIGNED ) ) )? otherlv_8= RightParenthesis
                    {
                    otherlv_4=(Token)match(input,LeftParenthesis,FOLLOW_12); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_4, grammarAccess.getOpFunctionCastAccess().getLeftParenthesisKeyword_4_0());
                          
                    }
                    // InternalSqlParser.g:6760:1: ( (lv_p_5_0= RULE_UNSIGNED ) )
                    // InternalSqlParser.g:6761:1: (lv_p_5_0= RULE_UNSIGNED )
                    {
                    // InternalSqlParser.g:6761:1: (lv_p_5_0= RULE_UNSIGNED )
                    // InternalSqlParser.g:6762:3: lv_p_5_0= RULE_UNSIGNED
                    {
                    lv_p_5_0=(Token)match(input,RULE_UNSIGNED,FOLLOW_93); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(lv_p_5_0, grammarAccess.getOpFunctionCastAccess().getPUNSIGNEDTerminalRuleCall_4_1_0()); 
                      		
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getOpFunctionCastRule());
                      	        }
                             		setWithLastConsumed(
                             			current, 
                             			"p",
                              		lv_p_5_0, 
                              		"com.jaspersoft.studio.data.Sql.UNSIGNED");
                      	    
                    }

                    }


                    }

                    // InternalSqlParser.g:6778:2: (otherlv_6= Comma ( (lv_p2_7_0= RULE_UNSIGNED ) ) )?
                    int alt127=2;
                    int LA127_0 = input.LA(1);

                    if ( (LA127_0==Comma) ) {
                        alt127=1;
                    }
                    switch (alt127) {
                        case 1 :
                            // InternalSqlParser.g:6779:2: otherlv_6= Comma ( (lv_p2_7_0= RULE_UNSIGNED ) )
                            {
                            otherlv_6=(Token)match(input,Comma,FOLLOW_12); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                  	newLeafNode(otherlv_6, grammarAccess.getOpFunctionCastAccess().getCommaKeyword_4_2_0());
                                  
                            }
                            // InternalSqlParser.g:6783:1: ( (lv_p2_7_0= RULE_UNSIGNED ) )
                            // InternalSqlParser.g:6784:1: (lv_p2_7_0= RULE_UNSIGNED )
                            {
                            // InternalSqlParser.g:6784:1: (lv_p2_7_0= RULE_UNSIGNED )
                            // InternalSqlParser.g:6785:3: lv_p2_7_0= RULE_UNSIGNED
                            {
                            lv_p2_7_0=(Token)match(input,RULE_UNSIGNED,FOLLOW_8); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              			newLeafNode(lv_p2_7_0, grammarAccess.getOpFunctionCastAccess().getP2UNSIGNEDTerminalRuleCall_4_2_1_0()); 
                              		
                            }
                            if ( state.backtracking==0 ) {

                              	        if (current==null) {
                              	            current = createModelElement(grammarAccess.getOpFunctionCastRule());
                              	        }
                                     		setWithLastConsumed(
                                     			current, 
                                     			"p2",
                                      		lv_p2_7_0, 
                                      		"com.jaspersoft.studio.data.Sql.UNSIGNED");
                              	    
                            }

                            }


                            }


                            }
                            break;

                    }

                    otherlv_8=(Token)match(input,RightParenthesis,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_8, grammarAccess.getOpFunctionCastAccess().getRightParenthesisKeyword_4_3());
                          
                    }

                    }
                    break;

            }

            otherlv_9=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_9, grammarAccess.getOpFunctionCastAccess().getRightParenthesisKeyword_5());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOpFunctionCast"


    // $ANTLR start "entryRuleOpFunctionArgAgregate"
    // InternalSqlParser.g:6819:1: entryRuleOpFunctionArgAgregate returns [EObject current=null] : iv_ruleOpFunctionArgAgregate= ruleOpFunctionArgAgregate EOF ;
    public final EObject entryRuleOpFunctionArgAgregate() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOpFunctionArgAgregate = null;


        try {
            // InternalSqlParser.g:6820:2: (iv_ruleOpFunctionArgAgregate= ruleOpFunctionArgAgregate EOF )
            // InternalSqlParser.g:6821:2: iv_ruleOpFunctionArgAgregate= ruleOpFunctionArgAgregate EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpFunctionArgAgregateRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOpFunctionArgAgregate=ruleOpFunctionArgAgregate();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpFunctionArgAgregate; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOpFunctionArgAgregate"


    // $ANTLR start "ruleOpFunctionArgAgregate"
    // InternalSqlParser.g:6828:1: ruleOpFunctionArgAgregate returns [EObject current=null] : ( (otherlv_0= ALL | otherlv_1= DISTINCT ) this_Operand_2= ruleOperand ) ;
    public final EObject ruleOpFunctionArgAgregate() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        EObject this_Operand_2 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:6831:28: ( ( (otherlv_0= ALL | otherlv_1= DISTINCT ) this_Operand_2= ruleOperand ) )
            // InternalSqlParser.g:6832:1: ( (otherlv_0= ALL | otherlv_1= DISTINCT ) this_Operand_2= ruleOperand )
            {
            // InternalSqlParser.g:6832:1: ( (otherlv_0= ALL | otherlv_1= DISTINCT ) this_Operand_2= ruleOperand )
            // InternalSqlParser.g:6832:2: (otherlv_0= ALL | otherlv_1= DISTINCT ) this_Operand_2= ruleOperand
            {
            // InternalSqlParser.g:6832:2: (otherlv_0= ALL | otherlv_1= DISTINCT )
            int alt129=2;
            int LA129_0 = input.LA(1);

            if ( (LA129_0==ALL) ) {
                alt129=1;
            }
            else if ( (LA129_0==DISTINCT) ) {
                alt129=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 129, 0, input);

                throw nvae;
            }
            switch (alt129) {
                case 1 :
                    // InternalSqlParser.g:6833:2: otherlv_0= ALL
                    {
                    otherlv_0=(Token)match(input,ALL,FOLLOW_60); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_0, grammarAccess.getOpFunctionArgAgregateAccess().getALLKeyword_0_0());
                          
                    }

                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:6839:2: otherlv_1= DISTINCT
                    {
                    otherlv_1=(Token)match(input,DISTINCT,FOLLOW_60); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_1, grammarAccess.getOpFunctionArgAgregateAccess().getDISTINCTKeyword_0_1());
                          
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getOpFunctionArgAgregateAccess().getOperandParserRuleCall_1()); 
                  
            }
            pushFollow(FOLLOW_2);
            this_Operand_2=ruleOperand();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_Operand_2;
                      afterParserOrEnumRuleCall();
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOpFunctionArgAgregate"


    // $ANTLR start "entryRuleXOperandFragment"
    // InternalSqlParser.g:6863:1: entryRuleXOperandFragment returns [EObject current=null] : iv_ruleXOperandFragment= ruleXOperandFragment EOF ;
    public final EObject entryRuleXOperandFragment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXOperandFragment = null;


        try {
            // InternalSqlParser.g:6864:2: (iv_ruleXOperandFragment= ruleXOperandFragment EOF )
            // InternalSqlParser.g:6865:2: iv_ruleXOperandFragment= ruleXOperandFragment EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXOperandFragmentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXOperandFragment=ruleXOperandFragment();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXOperandFragment; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXOperandFragment"


    // $ANTLR start "ruleXOperandFragment"
    // InternalSqlParser.g:6872:1: ruleXOperandFragment returns [EObject current=null] : ( ( (lv_param_0_0= ruleParameterOperand ) ) | ( (lv_eparam_1_0= ruleExclamationParameterOperand ) ) | ( (lv_scalar_2_0= ruleScalarNumberOperand ) ) ) ;
    public final EObject ruleXOperandFragment() throws RecognitionException {
        EObject current = null;

        EObject lv_param_0_0 = null;

        EObject lv_eparam_1_0 = null;

        EObject lv_scalar_2_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:6875:28: ( ( ( (lv_param_0_0= ruleParameterOperand ) ) | ( (lv_eparam_1_0= ruleExclamationParameterOperand ) ) | ( (lv_scalar_2_0= ruleScalarNumberOperand ) ) ) )
            // InternalSqlParser.g:6876:1: ( ( (lv_param_0_0= ruleParameterOperand ) ) | ( (lv_eparam_1_0= ruleExclamationParameterOperand ) ) | ( (lv_scalar_2_0= ruleScalarNumberOperand ) ) )
            {
            // InternalSqlParser.g:6876:1: ( ( (lv_param_0_0= ruleParameterOperand ) ) | ( (lv_eparam_1_0= ruleExclamationParameterOperand ) ) | ( (lv_scalar_2_0= ruleScalarNumberOperand ) ) )
            int alt130=3;
            switch ( input.LA(1) ) {
            case RULE_JRPARAM:
                {
                alt130=1;
                }
                break;
            case RULE_JRNPARAM:
                {
                alt130=2;
                }
                break;
            case RULE_UNSIGNED:
            case RULE_INT:
            case RULE_SIGNED_DOUBLE:
            case RULE_STRING_:
                {
                alt130=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 130, 0, input);

                throw nvae;
            }

            switch (alt130) {
                case 1 :
                    // InternalSqlParser.g:6876:2: ( (lv_param_0_0= ruleParameterOperand ) )
                    {
                    // InternalSqlParser.g:6876:2: ( (lv_param_0_0= ruleParameterOperand ) )
                    // InternalSqlParser.g:6877:1: (lv_param_0_0= ruleParameterOperand )
                    {
                    // InternalSqlParser.g:6877:1: (lv_param_0_0= ruleParameterOperand )
                    // InternalSqlParser.g:6878:3: lv_param_0_0= ruleParameterOperand
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getXOperandFragmentAccess().getParamParameterOperandParserRuleCall_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_param_0_0=ruleParameterOperand();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getXOperandFragmentRule());
                      	        }
                             		set(
                             			current, 
                             			"param",
                              		lv_param_0_0, 
                              		"com.jaspersoft.studio.data.Sql.ParameterOperand");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:6895:6: ( (lv_eparam_1_0= ruleExclamationParameterOperand ) )
                    {
                    // InternalSqlParser.g:6895:6: ( (lv_eparam_1_0= ruleExclamationParameterOperand ) )
                    // InternalSqlParser.g:6896:1: (lv_eparam_1_0= ruleExclamationParameterOperand )
                    {
                    // InternalSqlParser.g:6896:1: (lv_eparam_1_0= ruleExclamationParameterOperand )
                    // InternalSqlParser.g:6897:3: lv_eparam_1_0= ruleExclamationParameterOperand
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getXOperandFragmentAccess().getEparamExclamationParameterOperandParserRuleCall_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_eparam_1_0=ruleExclamationParameterOperand();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getXOperandFragmentRule());
                      	        }
                             		set(
                             			current, 
                             			"eparam",
                              		lv_eparam_1_0, 
                              		"com.jaspersoft.studio.data.Sql.ExclamationParameterOperand");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSqlParser.g:6914:6: ( (lv_scalar_2_0= ruleScalarNumberOperand ) )
                    {
                    // InternalSqlParser.g:6914:6: ( (lv_scalar_2_0= ruleScalarNumberOperand ) )
                    // InternalSqlParser.g:6915:1: (lv_scalar_2_0= ruleScalarNumberOperand )
                    {
                    // InternalSqlParser.g:6915:1: (lv_scalar_2_0= ruleScalarNumberOperand )
                    // InternalSqlParser.g:6916:3: lv_scalar_2_0= ruleScalarNumberOperand
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getXOperandFragmentAccess().getScalarScalarNumberOperandParserRuleCall_2_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_scalar_2_0=ruleScalarNumberOperand();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getXOperandFragmentRule());
                      	        }
                             		set(
                             			current, 
                             			"scalar",
                              		lv_scalar_2_0, 
                              		"com.jaspersoft.studio.data.Sql.ScalarNumberOperand");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXOperandFragment"


    // $ANTLR start "entryRuleParameterOperand"
    // InternalSqlParser.g:6940:1: entryRuleParameterOperand returns [EObject current=null] : iv_ruleParameterOperand= ruleParameterOperand EOF ;
    public final EObject entryRuleParameterOperand() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterOperand = null;


        try {
            // InternalSqlParser.g:6941:2: (iv_ruleParameterOperand= ruleParameterOperand EOF )
            // InternalSqlParser.g:6942:2: iv_ruleParameterOperand= ruleParameterOperand EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getParameterOperandRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleParameterOperand=ruleParameterOperand();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleParameterOperand; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParameterOperand"


    // $ANTLR start "ruleParameterOperand"
    // InternalSqlParser.g:6949:1: ruleParameterOperand returns [EObject current=null] : ( (lv_prm_0_0= RULE_JRPARAM ) ) ;
    public final EObject ruleParameterOperand() throws RecognitionException {
        EObject current = null;

        Token lv_prm_0_0=null;

         enterRule(); 
            
        try {
            // InternalSqlParser.g:6952:28: ( ( (lv_prm_0_0= RULE_JRPARAM ) ) )
            // InternalSqlParser.g:6953:1: ( (lv_prm_0_0= RULE_JRPARAM ) )
            {
            // InternalSqlParser.g:6953:1: ( (lv_prm_0_0= RULE_JRPARAM ) )
            // InternalSqlParser.g:6954:1: (lv_prm_0_0= RULE_JRPARAM )
            {
            // InternalSqlParser.g:6954:1: (lv_prm_0_0= RULE_JRPARAM )
            // InternalSqlParser.g:6955:3: lv_prm_0_0= RULE_JRPARAM
            {
            lv_prm_0_0=(Token)match(input,RULE_JRPARAM,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_prm_0_0, grammarAccess.getParameterOperandAccess().getPrmJRPARAMTerminalRuleCall_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getParameterOperandRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"prm",
                      		lv_prm_0_0, 
                      		"com.jaspersoft.studio.data.Sql.JRPARAM");
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParameterOperand"


    // $ANTLR start "entryRuleExclamationParameterOperand"
    // InternalSqlParser.g:6979:1: entryRuleExclamationParameterOperand returns [EObject current=null] : iv_ruleExclamationParameterOperand= ruleExclamationParameterOperand EOF ;
    public final EObject entryRuleExclamationParameterOperand() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExclamationParameterOperand = null;


        try {
            // InternalSqlParser.g:6980:2: (iv_ruleExclamationParameterOperand= ruleExclamationParameterOperand EOF )
            // InternalSqlParser.g:6981:2: iv_ruleExclamationParameterOperand= ruleExclamationParameterOperand EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExclamationParameterOperandRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleExclamationParameterOperand=ruleExclamationParameterOperand();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleExclamationParameterOperand; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExclamationParameterOperand"


    // $ANTLR start "ruleExclamationParameterOperand"
    // InternalSqlParser.g:6988:1: ruleExclamationParameterOperand returns [EObject current=null] : ( (lv_prm_0_0= RULE_JRNPARAM ) ) ;
    public final EObject ruleExclamationParameterOperand() throws RecognitionException {
        EObject current = null;

        Token lv_prm_0_0=null;

         enterRule(); 
            
        try {
            // InternalSqlParser.g:6991:28: ( ( (lv_prm_0_0= RULE_JRNPARAM ) ) )
            // InternalSqlParser.g:6992:1: ( (lv_prm_0_0= RULE_JRNPARAM ) )
            {
            // InternalSqlParser.g:6992:1: ( (lv_prm_0_0= RULE_JRNPARAM ) )
            // InternalSqlParser.g:6993:1: (lv_prm_0_0= RULE_JRNPARAM )
            {
            // InternalSqlParser.g:6993:1: (lv_prm_0_0= RULE_JRNPARAM )
            // InternalSqlParser.g:6994:3: lv_prm_0_0= RULE_JRNPARAM
            {
            lv_prm_0_0=(Token)match(input,RULE_JRNPARAM,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_prm_0_0, grammarAccess.getExclamationParameterOperandAccess().getPrmJRNPARAMTerminalRuleCall_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getExclamationParameterOperandRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"prm",
                      		lv_prm_0_0, 
                      		"com.jaspersoft.studio.data.Sql.JRNPARAM");
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExclamationParameterOperand"


    // $ANTLR start "entryRuleColumnOperand"
    // InternalSqlParser.g:7018:1: entryRuleColumnOperand returns [EObject current=null] : iv_ruleColumnOperand= ruleColumnOperand EOF ;
    public final EObject entryRuleColumnOperand() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleColumnOperand = null;


        try {
            // InternalSqlParser.g:7019:2: (iv_ruleColumnOperand= ruleColumnOperand EOF )
            // InternalSqlParser.g:7020:2: iv_ruleColumnOperand= ruleColumnOperand EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getColumnOperandRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleColumnOperand=ruleColumnOperand();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleColumnOperand; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleColumnOperand"


    // $ANTLR start "ruleColumnOperand"
    // InternalSqlParser.g:7027:1: ruleColumnOperand returns [EObject current=null] : ( ( (lv_cfull_0_0= ruleColumnFull ) ) ( (lv_ora_1_0= LeftParenthesisPlusSignRightParenthesis ) )? ) ;
    public final EObject ruleColumnOperand() throws RecognitionException {
        EObject current = null;

        Token lv_ora_1_0=null;
        EObject lv_cfull_0_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:7030:28: ( ( ( (lv_cfull_0_0= ruleColumnFull ) ) ( (lv_ora_1_0= LeftParenthesisPlusSignRightParenthesis ) )? ) )
            // InternalSqlParser.g:7031:1: ( ( (lv_cfull_0_0= ruleColumnFull ) ) ( (lv_ora_1_0= LeftParenthesisPlusSignRightParenthesis ) )? )
            {
            // InternalSqlParser.g:7031:1: ( ( (lv_cfull_0_0= ruleColumnFull ) ) ( (lv_ora_1_0= LeftParenthesisPlusSignRightParenthesis ) )? )
            // InternalSqlParser.g:7031:2: ( (lv_cfull_0_0= ruleColumnFull ) ) ( (lv_ora_1_0= LeftParenthesisPlusSignRightParenthesis ) )?
            {
            // InternalSqlParser.g:7031:2: ( (lv_cfull_0_0= ruleColumnFull ) )
            // InternalSqlParser.g:7032:1: (lv_cfull_0_0= ruleColumnFull )
            {
            // InternalSqlParser.g:7032:1: (lv_cfull_0_0= ruleColumnFull )
            // InternalSqlParser.g:7033:3: lv_cfull_0_0= ruleColumnFull
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getColumnOperandAccess().getCfullColumnFullParserRuleCall_0_0()); 
              	    
            }
            pushFollow(FOLLOW_94);
            lv_cfull_0_0=ruleColumnFull();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getColumnOperandRule());
              	        }
                     		set(
                     			current, 
                     			"cfull",
                      		lv_cfull_0_0, 
                      		"com.jaspersoft.studio.data.Sql.ColumnFull");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:7049:2: ( (lv_ora_1_0= LeftParenthesisPlusSignRightParenthesis ) )?
            int alt131=2;
            int LA131_0 = input.LA(1);

            if ( (LA131_0==LeftParenthesisPlusSignRightParenthesis) ) {
                alt131=1;
            }
            switch (alt131) {
                case 1 :
                    // InternalSqlParser.g:7050:1: (lv_ora_1_0= LeftParenthesisPlusSignRightParenthesis )
                    {
                    // InternalSqlParser.g:7050:1: (lv_ora_1_0= LeftParenthesisPlusSignRightParenthesis )
                    // InternalSqlParser.g:7051:3: lv_ora_1_0= LeftParenthesisPlusSignRightParenthesis
                    {
                    lv_ora_1_0=(Token)match(input,LeftParenthesisPlusSignRightParenthesis,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_ora_1_0, grammarAccess.getColumnOperandAccess().getOraLeftParenthesisPlusSignRightParenthesisKeyword_1_0());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getColumnOperandRule());
                      	        }
                             		setWithLastConsumed(current, "ora", lv_ora_1_0, "(+)");
                      	    
                    }

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleColumnOperand"


    // $ANTLR start "entryRuleSubQueryOperand"
    // InternalSqlParser.g:7073:1: entryRuleSubQueryOperand returns [EObject current=null] : iv_ruleSubQueryOperand= ruleSubQueryOperand EOF ;
    public final EObject entryRuleSubQueryOperand() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSubQueryOperand = null;


        try {
            // InternalSqlParser.g:7074:2: (iv_ruleSubQueryOperand= ruleSubQueryOperand EOF )
            // InternalSqlParser.g:7075:2: iv_ruleSubQueryOperand= ruleSubQueryOperand EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSubQueryOperandRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSubQueryOperand=ruleSubQueryOperand();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSubQueryOperand; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSubQueryOperand"


    // $ANTLR start "ruleSubQueryOperand"
    // InternalSqlParser.g:7082:1: ruleSubQueryOperand returns [EObject current=null] : ( () otherlv_1= LeftParenthesis ( (lv_sel_2_0= ruleSelectQuery ) ) otherlv_3= RightParenthesis ) ;
    public final EObject ruleSubQueryOperand() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_sel_2_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:7085:28: ( ( () otherlv_1= LeftParenthesis ( (lv_sel_2_0= ruleSelectQuery ) ) otherlv_3= RightParenthesis ) )
            // InternalSqlParser.g:7086:1: ( () otherlv_1= LeftParenthesis ( (lv_sel_2_0= ruleSelectQuery ) ) otherlv_3= RightParenthesis )
            {
            // InternalSqlParser.g:7086:1: ( () otherlv_1= LeftParenthesis ( (lv_sel_2_0= ruleSelectQuery ) ) otherlv_3= RightParenthesis )
            // InternalSqlParser.g:7086:2: () otherlv_1= LeftParenthesis ( (lv_sel_2_0= ruleSelectQuery ) ) otherlv_3= RightParenthesis
            {
            // InternalSqlParser.g:7086:2: ()
            // InternalSqlParser.g:7087:2: 
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getSubQueryOperandAccess().getSubQueryOperandAction_0(),
                          current);
                  
            }

            }

            otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getSubQueryOperandAccess().getLeftParenthesisKeyword_1());
                  
            }
            // InternalSqlParser.g:7100:1: ( (lv_sel_2_0= ruleSelectQuery ) )
            // InternalSqlParser.g:7101:1: (lv_sel_2_0= ruleSelectQuery )
            {
            // InternalSqlParser.g:7101:1: (lv_sel_2_0= ruleSelectQuery )
            // InternalSqlParser.g:7102:3: lv_sel_2_0= ruleSelectQuery
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getSubQueryOperandAccess().getSelSelectQueryParserRuleCall_2_0()); 
              	    
            }
            pushFollow(FOLLOW_8);
            lv_sel_2_0=ruleSelectQuery();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getSubQueryOperandRule());
              	        }
                     		set(
                     			current, 
                     			"sel",
                      		lv_sel_2_0, 
                      		"com.jaspersoft.studio.data.Sql.SelectQuery");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_3=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getSubQueryOperandAccess().getRightParenthesisKeyword_3());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSubQueryOperand"


    // $ANTLR start "entryRuleScalarOperand"
    // InternalSqlParser.g:7131:1: entryRuleScalarOperand returns [EObject current=null] : iv_ruleScalarOperand= ruleScalarOperand EOF ;
    public final EObject entryRuleScalarOperand() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleScalarOperand = null;


        try {
            // InternalSqlParser.g:7132:2: (iv_ruleScalarOperand= ruleScalarOperand EOF )
            // InternalSqlParser.g:7133:2: iv_ruleScalarOperand= ruleScalarOperand EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getScalarOperandRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleScalarOperand=ruleScalarOperand();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleScalarOperand; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleScalarOperand"


    // $ANTLR start "ruleScalarOperand"
    // InternalSqlParser.g:7140:1: ruleScalarOperand returns [EObject current=null] : ( ( (lv_sostr_0_0= ruleStringOperand ) ) | ( (lv_sodbl_1_0= RULE_SIGNED_DOUBLE ) ) | ( (lv_sodate_2_0= RULE_DATE ) ) | ( (lv_sotime_3_0= RULE_TIME ) ) | ( (lv_sodt_4_0= RULE_TIMESTAMP ) ) ) ;
    public final EObject ruleScalarOperand() throws RecognitionException {
        EObject current = null;

        Token lv_sodbl_1_0=null;
        Token lv_sodate_2_0=null;
        Token lv_sotime_3_0=null;
        Token lv_sodt_4_0=null;
        AntlrDatatypeRuleToken lv_sostr_0_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:7143:28: ( ( ( (lv_sostr_0_0= ruleStringOperand ) ) | ( (lv_sodbl_1_0= RULE_SIGNED_DOUBLE ) ) | ( (lv_sodate_2_0= RULE_DATE ) ) | ( (lv_sotime_3_0= RULE_TIME ) ) | ( (lv_sodt_4_0= RULE_TIMESTAMP ) ) ) )
            // InternalSqlParser.g:7144:1: ( ( (lv_sostr_0_0= ruleStringOperand ) ) | ( (lv_sodbl_1_0= RULE_SIGNED_DOUBLE ) ) | ( (lv_sodate_2_0= RULE_DATE ) ) | ( (lv_sotime_3_0= RULE_TIME ) ) | ( (lv_sodt_4_0= RULE_TIMESTAMP ) ) )
            {
            // InternalSqlParser.g:7144:1: ( ( (lv_sostr_0_0= ruleStringOperand ) ) | ( (lv_sodbl_1_0= RULE_SIGNED_DOUBLE ) ) | ( (lv_sodate_2_0= RULE_DATE ) ) | ( (lv_sotime_3_0= RULE_TIME ) ) | ( (lv_sodt_4_0= RULE_TIMESTAMP ) ) )
            int alt132=5;
            switch ( input.LA(1) ) {
            case RULE_STRING_:
                {
                alt132=1;
                }
                break;
            case RULE_SIGNED_DOUBLE:
                {
                alt132=2;
                }
                break;
            case RULE_DATE:
                {
                alt132=3;
                }
                break;
            case RULE_TIME:
                {
                alt132=4;
                }
                break;
            case RULE_TIMESTAMP:
                {
                alt132=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 132, 0, input);

                throw nvae;
            }

            switch (alt132) {
                case 1 :
                    // InternalSqlParser.g:7144:2: ( (lv_sostr_0_0= ruleStringOperand ) )
                    {
                    // InternalSqlParser.g:7144:2: ( (lv_sostr_0_0= ruleStringOperand ) )
                    // InternalSqlParser.g:7145:1: (lv_sostr_0_0= ruleStringOperand )
                    {
                    // InternalSqlParser.g:7145:1: (lv_sostr_0_0= ruleStringOperand )
                    // InternalSqlParser.g:7146:3: lv_sostr_0_0= ruleStringOperand
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getScalarOperandAccess().getSostrStringOperandParserRuleCall_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_sostr_0_0=ruleStringOperand();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getScalarOperandRule());
                      	        }
                             		set(
                             			current, 
                             			"sostr",
                              		lv_sostr_0_0, 
                              		"com.jaspersoft.studio.data.Sql.StringOperand");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:7163:6: ( (lv_sodbl_1_0= RULE_SIGNED_DOUBLE ) )
                    {
                    // InternalSqlParser.g:7163:6: ( (lv_sodbl_1_0= RULE_SIGNED_DOUBLE ) )
                    // InternalSqlParser.g:7164:1: (lv_sodbl_1_0= RULE_SIGNED_DOUBLE )
                    {
                    // InternalSqlParser.g:7164:1: (lv_sodbl_1_0= RULE_SIGNED_DOUBLE )
                    // InternalSqlParser.g:7165:3: lv_sodbl_1_0= RULE_SIGNED_DOUBLE
                    {
                    lv_sodbl_1_0=(Token)match(input,RULE_SIGNED_DOUBLE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(lv_sodbl_1_0, grammarAccess.getScalarOperandAccess().getSodblSIGNED_DOUBLETerminalRuleCall_1_0()); 
                      		
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getScalarOperandRule());
                      	        }
                             		setWithLastConsumed(
                             			current, 
                             			"sodbl",
                              		lv_sodbl_1_0, 
                              		"com.jaspersoft.studio.data.Sql.SIGNED_DOUBLE");
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSqlParser.g:7182:6: ( (lv_sodate_2_0= RULE_DATE ) )
                    {
                    // InternalSqlParser.g:7182:6: ( (lv_sodate_2_0= RULE_DATE ) )
                    // InternalSqlParser.g:7183:1: (lv_sodate_2_0= RULE_DATE )
                    {
                    // InternalSqlParser.g:7183:1: (lv_sodate_2_0= RULE_DATE )
                    // InternalSqlParser.g:7184:3: lv_sodate_2_0= RULE_DATE
                    {
                    lv_sodate_2_0=(Token)match(input,RULE_DATE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(lv_sodate_2_0, grammarAccess.getScalarOperandAccess().getSodateDATETerminalRuleCall_2_0()); 
                      		
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getScalarOperandRule());
                      	        }
                             		setWithLastConsumed(
                             			current, 
                             			"sodate",
                              		lv_sodate_2_0, 
                              		"com.jaspersoft.studio.data.Sql.DATE");
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalSqlParser.g:7201:6: ( (lv_sotime_3_0= RULE_TIME ) )
                    {
                    // InternalSqlParser.g:7201:6: ( (lv_sotime_3_0= RULE_TIME ) )
                    // InternalSqlParser.g:7202:1: (lv_sotime_3_0= RULE_TIME )
                    {
                    // InternalSqlParser.g:7202:1: (lv_sotime_3_0= RULE_TIME )
                    // InternalSqlParser.g:7203:3: lv_sotime_3_0= RULE_TIME
                    {
                    lv_sotime_3_0=(Token)match(input,RULE_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(lv_sotime_3_0, grammarAccess.getScalarOperandAccess().getSotimeTIMETerminalRuleCall_3_0()); 
                      		
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getScalarOperandRule());
                      	        }
                             		setWithLastConsumed(
                             			current, 
                             			"sotime",
                              		lv_sotime_3_0, 
                              		"com.jaspersoft.studio.data.Sql.TIME");
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalSqlParser.g:7220:6: ( (lv_sodt_4_0= RULE_TIMESTAMP ) )
                    {
                    // InternalSqlParser.g:7220:6: ( (lv_sodt_4_0= RULE_TIMESTAMP ) )
                    // InternalSqlParser.g:7221:1: (lv_sodt_4_0= RULE_TIMESTAMP )
                    {
                    // InternalSqlParser.g:7221:1: (lv_sodt_4_0= RULE_TIMESTAMP )
                    // InternalSqlParser.g:7222:3: lv_sodt_4_0= RULE_TIMESTAMP
                    {
                    lv_sodt_4_0=(Token)match(input,RULE_TIMESTAMP,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(lv_sodt_4_0, grammarAccess.getScalarOperandAccess().getSodtTIMESTAMPTerminalRuleCall_4_0()); 
                      		
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getScalarOperandRule());
                      	        }
                             		setWithLastConsumed(
                             			current, 
                             			"sodt",
                              		lv_sodt_4_0, 
                              		"com.jaspersoft.studio.data.Sql.TIMESTAMP");
                      	    
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleScalarOperand"


    // $ANTLR start "entryRuleScalarNumberOperand"
    // InternalSqlParser.g:7246:1: entryRuleScalarNumberOperand returns [EObject current=null] : iv_ruleScalarNumberOperand= ruleScalarNumberOperand EOF ;
    public final EObject entryRuleScalarNumberOperand() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleScalarNumberOperand = null;


        try {
            // InternalSqlParser.g:7247:2: (iv_ruleScalarNumberOperand= ruleScalarNumberOperand EOF )
            // InternalSqlParser.g:7248:2: iv_ruleScalarNumberOperand= ruleScalarNumberOperand EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getScalarNumberOperandRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleScalarNumberOperand=ruleScalarNumberOperand();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleScalarNumberOperand; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleScalarNumberOperand"


    // $ANTLR start "ruleScalarNumberOperand"
    // InternalSqlParser.g:7255:1: ruleScalarNumberOperand returns [EObject current=null] : ( ( (lv_soUInt_0_0= RULE_UNSIGNED ) ) | ( (lv_soint_1_0= RULE_INT ) ) | ( (lv_sodbl_2_0= RULE_SIGNED_DOUBLE ) ) | ( (lv_sostr_3_0= ruleStringOperand ) ) ) ;
    public final EObject ruleScalarNumberOperand() throws RecognitionException {
        EObject current = null;

        Token lv_soUInt_0_0=null;
        Token lv_soint_1_0=null;
        Token lv_sodbl_2_0=null;
        AntlrDatatypeRuleToken lv_sostr_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:7258:28: ( ( ( (lv_soUInt_0_0= RULE_UNSIGNED ) ) | ( (lv_soint_1_0= RULE_INT ) ) | ( (lv_sodbl_2_0= RULE_SIGNED_DOUBLE ) ) | ( (lv_sostr_3_0= ruleStringOperand ) ) ) )
            // InternalSqlParser.g:7259:1: ( ( (lv_soUInt_0_0= RULE_UNSIGNED ) ) | ( (lv_soint_1_0= RULE_INT ) ) | ( (lv_sodbl_2_0= RULE_SIGNED_DOUBLE ) ) | ( (lv_sostr_3_0= ruleStringOperand ) ) )
            {
            // InternalSqlParser.g:7259:1: ( ( (lv_soUInt_0_0= RULE_UNSIGNED ) ) | ( (lv_soint_1_0= RULE_INT ) ) | ( (lv_sodbl_2_0= RULE_SIGNED_DOUBLE ) ) | ( (lv_sostr_3_0= ruleStringOperand ) ) )
            int alt133=4;
            switch ( input.LA(1) ) {
            case RULE_UNSIGNED:
                {
                alt133=1;
                }
                break;
            case RULE_INT:
                {
                alt133=2;
                }
                break;
            case RULE_SIGNED_DOUBLE:
                {
                alt133=3;
                }
                break;
            case RULE_STRING_:
                {
                alt133=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 133, 0, input);

                throw nvae;
            }

            switch (alt133) {
                case 1 :
                    // InternalSqlParser.g:7259:2: ( (lv_soUInt_0_0= RULE_UNSIGNED ) )
                    {
                    // InternalSqlParser.g:7259:2: ( (lv_soUInt_0_0= RULE_UNSIGNED ) )
                    // InternalSqlParser.g:7260:1: (lv_soUInt_0_0= RULE_UNSIGNED )
                    {
                    // InternalSqlParser.g:7260:1: (lv_soUInt_0_0= RULE_UNSIGNED )
                    // InternalSqlParser.g:7261:3: lv_soUInt_0_0= RULE_UNSIGNED
                    {
                    lv_soUInt_0_0=(Token)match(input,RULE_UNSIGNED,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(lv_soUInt_0_0, grammarAccess.getScalarNumberOperandAccess().getSoUIntUNSIGNEDTerminalRuleCall_0_0()); 
                      		
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getScalarNumberOperandRule());
                      	        }
                             		setWithLastConsumed(
                             			current, 
                             			"soUInt",
                              		lv_soUInt_0_0, 
                              		"com.jaspersoft.studio.data.Sql.UNSIGNED");
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:7278:6: ( (lv_soint_1_0= RULE_INT ) )
                    {
                    // InternalSqlParser.g:7278:6: ( (lv_soint_1_0= RULE_INT ) )
                    // InternalSqlParser.g:7279:1: (lv_soint_1_0= RULE_INT )
                    {
                    // InternalSqlParser.g:7279:1: (lv_soint_1_0= RULE_INT )
                    // InternalSqlParser.g:7280:3: lv_soint_1_0= RULE_INT
                    {
                    lv_soint_1_0=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(lv_soint_1_0, grammarAccess.getScalarNumberOperandAccess().getSointINTTerminalRuleCall_1_0()); 
                      		
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getScalarNumberOperandRule());
                      	        }
                             		setWithLastConsumed(
                             			current, 
                             			"soint",
                              		lv_soint_1_0, 
                              		"com.jaspersoft.studio.data.Sql.INT");
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSqlParser.g:7297:6: ( (lv_sodbl_2_0= RULE_SIGNED_DOUBLE ) )
                    {
                    // InternalSqlParser.g:7297:6: ( (lv_sodbl_2_0= RULE_SIGNED_DOUBLE ) )
                    // InternalSqlParser.g:7298:1: (lv_sodbl_2_0= RULE_SIGNED_DOUBLE )
                    {
                    // InternalSqlParser.g:7298:1: (lv_sodbl_2_0= RULE_SIGNED_DOUBLE )
                    // InternalSqlParser.g:7299:3: lv_sodbl_2_0= RULE_SIGNED_DOUBLE
                    {
                    lv_sodbl_2_0=(Token)match(input,RULE_SIGNED_DOUBLE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(lv_sodbl_2_0, grammarAccess.getScalarNumberOperandAccess().getSodblSIGNED_DOUBLETerminalRuleCall_2_0()); 
                      		
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getScalarNumberOperandRule());
                      	        }
                             		setWithLastConsumed(
                             			current, 
                             			"sodbl",
                              		lv_sodbl_2_0, 
                              		"com.jaspersoft.studio.data.Sql.SIGNED_DOUBLE");
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalSqlParser.g:7316:6: ( (lv_sostr_3_0= ruleStringOperand ) )
                    {
                    // InternalSqlParser.g:7316:6: ( (lv_sostr_3_0= ruleStringOperand ) )
                    // InternalSqlParser.g:7317:1: (lv_sostr_3_0= ruleStringOperand )
                    {
                    // InternalSqlParser.g:7317:1: (lv_sostr_3_0= ruleStringOperand )
                    // InternalSqlParser.g:7318:3: lv_sostr_3_0= ruleStringOperand
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getScalarNumberOperandAccess().getSostrStringOperandParserRuleCall_3_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_sostr_3_0=ruleStringOperand();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getScalarNumberOperandRule());
                      	        }
                             		set(
                             			current, 
                             			"sostr",
                              		lv_sostr_3_0, 
                              		"com.jaspersoft.studio.data.Sql.StringOperand");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleScalarNumberOperand"


    // $ANTLR start "entryRuleSQLCASE"
    // InternalSqlParser.g:7342:1: entryRuleSQLCASE returns [EObject current=null] : iv_ruleSQLCASE= ruleSQLCASE EOF ;
    public final EObject entryRuleSQLCASE() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSQLCASE = null;


        try {
            // InternalSqlParser.g:7343:2: (iv_ruleSQLCASE= ruleSQLCASE EOF )
            // InternalSqlParser.g:7344:2: iv_ruleSQLCASE= ruleSQLCASE EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSQLCASERule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSQLCASE=ruleSQLCASE();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSQLCASE; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSQLCASE"


    // $ANTLR start "ruleSQLCASE"
    // InternalSqlParser.g:7351:1: ruleSQLCASE returns [EObject current=null] : (otherlv_0= CASE ( ( (lv_wop_1_0= ruleOperandGroup ) ) | ( (lv_expr_2_0= ruleFullExpression ) ) )? ( (lv_when_3_0= ruleSQLCaseWhens ) ) otherlv_4= END ) ;
    public final EObject ruleSQLCASE() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_4=null;
        EObject lv_wop_1_0 = null;

        EObject lv_expr_2_0 = null;

        EObject lv_when_3_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:7354:28: ( (otherlv_0= CASE ( ( (lv_wop_1_0= ruleOperandGroup ) ) | ( (lv_expr_2_0= ruleFullExpression ) ) )? ( (lv_when_3_0= ruleSQLCaseWhens ) ) otherlv_4= END ) )
            // InternalSqlParser.g:7355:1: (otherlv_0= CASE ( ( (lv_wop_1_0= ruleOperandGroup ) ) | ( (lv_expr_2_0= ruleFullExpression ) ) )? ( (lv_when_3_0= ruleSQLCaseWhens ) ) otherlv_4= END )
            {
            // InternalSqlParser.g:7355:1: (otherlv_0= CASE ( ( (lv_wop_1_0= ruleOperandGroup ) ) | ( (lv_expr_2_0= ruleFullExpression ) ) )? ( (lv_when_3_0= ruleSQLCaseWhens ) ) otherlv_4= END )
            // InternalSqlParser.g:7356:2: otherlv_0= CASE ( ( (lv_wop_1_0= ruleOperandGroup ) ) | ( (lv_expr_2_0= ruleFullExpression ) ) )? ( (lv_when_3_0= ruleSQLCaseWhens ) ) otherlv_4= END
            {
            otherlv_0=(Token)match(input,CASE,FOLLOW_95); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getSQLCASEAccess().getCASEKeyword_0());
                  
            }
            // InternalSqlParser.g:7360:1: ( ( (lv_wop_1_0= ruleOperandGroup ) ) | ( (lv_expr_2_0= ruleFullExpression ) ) )?
            int alt134=3;
            alt134 = dfa134.predict(input);
            switch (alt134) {
                case 1 :
                    // InternalSqlParser.g:7360:2: ( (lv_wop_1_0= ruleOperandGroup ) )
                    {
                    // InternalSqlParser.g:7360:2: ( (lv_wop_1_0= ruleOperandGroup ) )
                    // InternalSqlParser.g:7361:1: (lv_wop_1_0= ruleOperandGroup )
                    {
                    // InternalSqlParser.g:7361:1: (lv_wop_1_0= ruleOperandGroup )
                    // InternalSqlParser.g:7362:3: lv_wop_1_0= ruleOperandGroup
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getSQLCASEAccess().getWopOperandGroupParserRuleCall_1_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_95);
                    lv_wop_1_0=ruleOperandGroup();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getSQLCASERule());
                      	        }
                             		set(
                             			current, 
                             			"wop",
                              		lv_wop_1_0, 
                              		"com.jaspersoft.studio.data.Sql.OperandGroup");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:7379:6: ( (lv_expr_2_0= ruleFullExpression ) )
                    {
                    // InternalSqlParser.g:7379:6: ( (lv_expr_2_0= ruleFullExpression ) )
                    // InternalSqlParser.g:7380:1: (lv_expr_2_0= ruleFullExpression )
                    {
                    // InternalSqlParser.g:7380:1: (lv_expr_2_0= ruleFullExpression )
                    // InternalSqlParser.g:7381:3: lv_expr_2_0= ruleFullExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getSQLCASEAccess().getExprFullExpressionParserRuleCall_1_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_95);
                    lv_expr_2_0=ruleFullExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getSQLCASERule());
                      	        }
                             		set(
                             			current, 
                             			"expr",
                              		lv_expr_2_0, 
                              		"com.jaspersoft.studio.data.Sql.FullExpression");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalSqlParser.g:7397:4: ( (lv_when_3_0= ruleSQLCaseWhens ) )
            // InternalSqlParser.g:7398:1: (lv_when_3_0= ruleSQLCaseWhens )
            {
            // InternalSqlParser.g:7398:1: (lv_when_3_0= ruleSQLCaseWhens )
            // InternalSqlParser.g:7399:3: lv_when_3_0= ruleSQLCaseWhens
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getSQLCASEAccess().getWhenSQLCaseWhensParserRuleCall_2_0()); 
              	    
            }
            pushFollow(FOLLOW_96);
            lv_when_3_0=ruleSQLCaseWhens();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getSQLCASERule());
              	        }
                     		set(
                     			current, 
                     			"when",
                      		lv_when_3_0, 
                      		"com.jaspersoft.studio.data.Sql.SQLCaseWhens");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_4=(Token)match(input,END,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_4, grammarAccess.getSQLCASEAccess().getENDKeyword_3());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSQLCASE"


    // $ANTLR start "entryRuleSQLCaseWhens"
    // InternalSqlParser.g:7428:1: entryRuleSQLCaseWhens returns [EObject current=null] : iv_ruleSQLCaseWhens= ruleSQLCaseWhens EOF ;
    public final EObject entryRuleSQLCaseWhens() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSQLCaseWhens = null;


        try {
            // InternalSqlParser.g:7429:2: (iv_ruleSQLCaseWhens= ruleSQLCaseWhens EOF )
            // InternalSqlParser.g:7430:2: iv_ruleSQLCaseWhens= ruleSQLCaseWhens EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSQLCaseWhensRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSQLCaseWhens=ruleSQLCaseWhens();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSQLCaseWhens; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSQLCaseWhens"


    // $ANTLR start "ruleSQLCaseWhens"
    // InternalSqlParser.g:7437:1: ruleSQLCaseWhens returns [EObject current=null] : (this_SqlCaseWhen_0= ruleSqlCaseWhen ( () ( (lv_entries_2_0= ruleSqlCaseWhen ) )+ )? ) ;
    public final EObject ruleSQLCaseWhens() throws RecognitionException {
        EObject current = null;

        EObject this_SqlCaseWhen_0 = null;

        EObject lv_entries_2_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:7440:28: ( (this_SqlCaseWhen_0= ruleSqlCaseWhen ( () ( (lv_entries_2_0= ruleSqlCaseWhen ) )+ )? ) )
            // InternalSqlParser.g:7441:1: (this_SqlCaseWhen_0= ruleSqlCaseWhen ( () ( (lv_entries_2_0= ruleSqlCaseWhen ) )+ )? )
            {
            // InternalSqlParser.g:7441:1: (this_SqlCaseWhen_0= ruleSqlCaseWhen ( () ( (lv_entries_2_0= ruleSqlCaseWhen ) )+ )? )
            // InternalSqlParser.g:7442:2: this_SqlCaseWhen_0= ruleSqlCaseWhen ( () ( (lv_entries_2_0= ruleSqlCaseWhen ) )+ )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getSQLCaseWhensAccess().getSqlCaseWhenParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_97);
            this_SqlCaseWhen_0=ruleSqlCaseWhen();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_SqlCaseWhen_0;
                      afterParserOrEnumRuleCall();
                  
            }
            // InternalSqlParser.g:7453:1: ( () ( (lv_entries_2_0= ruleSqlCaseWhen ) )+ )?
            int alt136=2;
            int LA136_0 = input.LA(1);

            if ( (LA136_0==WHEN) ) {
                alt136=1;
            }
            switch (alt136) {
                case 1 :
                    // InternalSqlParser.g:7453:2: () ( (lv_entries_2_0= ruleSqlCaseWhen ) )+
                    {
                    // InternalSqlParser.g:7453:2: ()
                    // InternalSqlParser.g:7454:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getSQLCaseWhensAccess().getWhenListEntriesAction_1_0(),
                                  current);
                          
                    }

                    }

                    // InternalSqlParser.g:7462:2: ( (lv_entries_2_0= ruleSqlCaseWhen ) )+
                    int cnt135=0;
                    loop135:
                    do {
                        int alt135=2;
                        int LA135_0 = input.LA(1);

                        if ( (LA135_0==WHEN) ) {
                            alt135=1;
                        }


                        switch (alt135) {
                    	case 1 :
                    	    // InternalSqlParser.g:7463:1: (lv_entries_2_0= ruleSqlCaseWhen )
                    	    {
                    	    // InternalSqlParser.g:7463:1: (lv_entries_2_0= ruleSqlCaseWhen )
                    	    // InternalSqlParser.g:7464:3: lv_entries_2_0= ruleSqlCaseWhen
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getSQLCaseWhensAccess().getEntriesSqlCaseWhenParserRuleCall_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_97);
                    	    lv_entries_2_0=ruleSqlCaseWhen();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getSQLCaseWhensRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"entries",
                    	              		lv_entries_2_0, 
                    	              		"com.jaspersoft.studio.data.Sql.SqlCaseWhen");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt135 >= 1 ) break loop135;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(135, input);
                                throw eee;
                        }
                        cnt135++;
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSQLCaseWhens"


    // $ANTLR start "entryRuleSqlCaseWhen"
    // InternalSqlParser.g:7488:1: entryRuleSqlCaseWhen returns [EObject current=null] : iv_ruleSqlCaseWhen= ruleSqlCaseWhen EOF ;
    public final EObject entryRuleSqlCaseWhen() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSqlCaseWhen = null;


        try {
            // InternalSqlParser.g:7489:2: (iv_ruleSqlCaseWhen= ruleSqlCaseWhen EOF )
            // InternalSqlParser.g:7490:2: iv_ruleSqlCaseWhen= ruleSqlCaseWhen EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSqlCaseWhenRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSqlCaseWhen=ruleSqlCaseWhen();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSqlCaseWhen; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSqlCaseWhen"


    // $ANTLR start "ruleSqlCaseWhen"
    // InternalSqlParser.g:7497:1: ruleSqlCaseWhen returns [EObject current=null] : (otherlv_0= WHEN ( ( (lv_wop_1_0= ruleOperandGroup ) ) | ( (lv_expr_2_0= ruleFullExpression ) ) ) otherlv_3= THEN ( (lv_texp_4_0= ruleOperandGroup ) ) (otherlv_5= ELSE ( (lv_eexp_6_0= ruleOperandGroup ) ) )? ) ;
    public final EObject ruleSqlCaseWhen() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_wop_1_0 = null;

        EObject lv_expr_2_0 = null;

        EObject lv_texp_4_0 = null;

        EObject lv_eexp_6_0 = null;


         enterRule(); 
            
        try {
            // InternalSqlParser.g:7500:28: ( (otherlv_0= WHEN ( ( (lv_wop_1_0= ruleOperandGroup ) ) | ( (lv_expr_2_0= ruleFullExpression ) ) ) otherlv_3= THEN ( (lv_texp_4_0= ruleOperandGroup ) ) (otherlv_5= ELSE ( (lv_eexp_6_0= ruleOperandGroup ) ) )? ) )
            // InternalSqlParser.g:7501:1: (otherlv_0= WHEN ( ( (lv_wop_1_0= ruleOperandGroup ) ) | ( (lv_expr_2_0= ruleFullExpression ) ) ) otherlv_3= THEN ( (lv_texp_4_0= ruleOperandGroup ) ) (otherlv_5= ELSE ( (lv_eexp_6_0= ruleOperandGroup ) ) )? )
            {
            // InternalSqlParser.g:7501:1: (otherlv_0= WHEN ( ( (lv_wop_1_0= ruleOperandGroup ) ) | ( (lv_expr_2_0= ruleFullExpression ) ) ) otherlv_3= THEN ( (lv_texp_4_0= ruleOperandGroup ) ) (otherlv_5= ELSE ( (lv_eexp_6_0= ruleOperandGroup ) ) )? )
            // InternalSqlParser.g:7502:2: otherlv_0= WHEN ( ( (lv_wop_1_0= ruleOperandGroup ) ) | ( (lv_expr_2_0= ruleFullExpression ) ) ) otherlv_3= THEN ( (lv_texp_4_0= ruleOperandGroup ) ) (otherlv_5= ELSE ( (lv_eexp_6_0= ruleOperandGroup ) ) )?
            {
            otherlv_0=(Token)match(input,WHEN,FOLLOW_23); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getSqlCaseWhenAccess().getWHENKeyword_0());
                  
            }
            // InternalSqlParser.g:7506:1: ( ( (lv_wop_1_0= ruleOperandGroup ) ) | ( (lv_expr_2_0= ruleFullExpression ) ) )
            int alt137=2;
            alt137 = dfa137.predict(input);
            switch (alt137) {
                case 1 :
                    // InternalSqlParser.g:7506:2: ( (lv_wop_1_0= ruleOperandGroup ) )
                    {
                    // InternalSqlParser.g:7506:2: ( (lv_wop_1_0= ruleOperandGroup ) )
                    // InternalSqlParser.g:7507:1: (lv_wop_1_0= ruleOperandGroup )
                    {
                    // InternalSqlParser.g:7507:1: (lv_wop_1_0= ruleOperandGroup )
                    // InternalSqlParser.g:7508:3: lv_wop_1_0= ruleOperandGroup
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getSqlCaseWhenAccess().getWopOperandGroupParserRuleCall_1_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_98);
                    lv_wop_1_0=ruleOperandGroup();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getSqlCaseWhenRule());
                      	        }
                             		set(
                             			current, 
                             			"wop",
                              		lv_wop_1_0, 
                              		"com.jaspersoft.studio.data.Sql.OperandGroup");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:7525:6: ( (lv_expr_2_0= ruleFullExpression ) )
                    {
                    // InternalSqlParser.g:7525:6: ( (lv_expr_2_0= ruleFullExpression ) )
                    // InternalSqlParser.g:7526:1: (lv_expr_2_0= ruleFullExpression )
                    {
                    // InternalSqlParser.g:7526:1: (lv_expr_2_0= ruleFullExpression )
                    // InternalSqlParser.g:7527:3: lv_expr_2_0= ruleFullExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getSqlCaseWhenAccess().getExprFullExpressionParserRuleCall_1_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_98);
                    lv_expr_2_0=ruleFullExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getSqlCaseWhenRule());
                      	        }
                             		set(
                             			current, 
                             			"expr",
                              		lv_expr_2_0, 
                              		"com.jaspersoft.studio.data.Sql.FullExpression");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_3=(Token)match(input,THEN,FOLLOW_60); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getSqlCaseWhenAccess().getTHENKeyword_2());
                  
            }
            // InternalSqlParser.g:7548:1: ( (lv_texp_4_0= ruleOperandGroup ) )
            // InternalSqlParser.g:7549:1: (lv_texp_4_0= ruleOperandGroup )
            {
            // InternalSqlParser.g:7549:1: (lv_texp_4_0= ruleOperandGroup )
            // InternalSqlParser.g:7550:3: lv_texp_4_0= ruleOperandGroup
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getSqlCaseWhenAccess().getTexpOperandGroupParserRuleCall_3_0()); 
              	    
            }
            pushFollow(FOLLOW_99);
            lv_texp_4_0=ruleOperandGroup();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getSqlCaseWhenRule());
              	        }
                     		set(
                     			current, 
                     			"texp",
                      		lv_texp_4_0, 
                      		"com.jaspersoft.studio.data.Sql.OperandGroup");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalSqlParser.g:7566:2: (otherlv_5= ELSE ( (lv_eexp_6_0= ruleOperandGroup ) ) )?
            int alt138=2;
            int LA138_0 = input.LA(1);

            if ( (LA138_0==ELSE) ) {
                alt138=1;
            }
            switch (alt138) {
                case 1 :
                    // InternalSqlParser.g:7567:2: otherlv_5= ELSE ( (lv_eexp_6_0= ruleOperandGroup ) )
                    {
                    otherlv_5=(Token)match(input,ELSE,FOLLOW_60); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_5, grammarAccess.getSqlCaseWhenAccess().getELSEKeyword_4_0());
                          
                    }
                    // InternalSqlParser.g:7571:1: ( (lv_eexp_6_0= ruleOperandGroup ) )
                    // InternalSqlParser.g:7572:1: (lv_eexp_6_0= ruleOperandGroup )
                    {
                    // InternalSqlParser.g:7572:1: (lv_eexp_6_0= ruleOperandGroup )
                    // InternalSqlParser.g:7573:3: lv_eexp_6_0= ruleOperandGroup
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getSqlCaseWhenAccess().getEexpOperandGroupParserRuleCall_4_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_eexp_6_0=ruleOperandGroup();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getSqlCaseWhenRule());
                      	        }
                             		set(
                             			current, 
                             			"eexp",
                              		lv_eexp_6_0, 
                              		"com.jaspersoft.studio.data.Sql.OperandGroup");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSqlCaseWhen"


    // $ANTLR start "entryRuleJoinType"
    // InternalSqlParser.g:7597:1: entryRuleJoinType returns [String current=null] : iv_ruleJoinType= ruleJoinType EOF ;
    public final String entryRuleJoinType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleJoinType = null;


        try {
            // InternalSqlParser.g:7598:1: (iv_ruleJoinType= ruleJoinType EOF )
            // InternalSqlParser.g:7599:2: iv_ruleJoinType= ruleJoinType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJoinTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleJoinType=ruleJoinType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJoinType.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJoinType"


    // $ANTLR start "ruleJoinType"
    // InternalSqlParser.g:7606:1: ruleJoinType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= NATURAL )? (kw= INNER | ( (kw= LEFT | kw= RIGHT | kw= FULL ) (kw= OUTER )? ) | kw= CROSS | kw= STRAIGHT_JOIN )? kw= JOIN ) ;
    public final AntlrDatatypeRuleToken ruleJoinType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // InternalSqlParser.g:7610:6: ( ( (kw= NATURAL )? (kw= INNER | ( (kw= LEFT | kw= RIGHT | kw= FULL ) (kw= OUTER )? ) | kw= CROSS | kw= STRAIGHT_JOIN )? kw= JOIN ) )
            // InternalSqlParser.g:7611:1: ( (kw= NATURAL )? (kw= INNER | ( (kw= LEFT | kw= RIGHT | kw= FULL ) (kw= OUTER )? ) | kw= CROSS | kw= STRAIGHT_JOIN )? kw= JOIN )
            {
            // InternalSqlParser.g:7611:1: ( (kw= NATURAL )? (kw= INNER | ( (kw= LEFT | kw= RIGHT | kw= FULL ) (kw= OUTER )? ) | kw= CROSS | kw= STRAIGHT_JOIN )? kw= JOIN )
            // InternalSqlParser.g:7611:2: (kw= NATURAL )? (kw= INNER | ( (kw= LEFT | kw= RIGHT | kw= FULL ) (kw= OUTER )? ) | kw= CROSS | kw= STRAIGHT_JOIN )? kw= JOIN
            {
            // InternalSqlParser.g:7611:2: (kw= NATURAL )?
            int alt139=2;
            int LA139_0 = input.LA(1);

            if ( (LA139_0==NATURAL) ) {
                alt139=1;
            }
            switch (alt139) {
                case 1 :
                    // InternalSqlParser.g:7612:2: kw= NATURAL
                    {
                    kw=(Token)match(input,NATURAL,FOLLOW_100); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getJoinTypeAccess().getNATURALKeyword_0()); 
                          
                    }

                    }
                    break;

            }

            // InternalSqlParser.g:7617:3: (kw= INNER | ( (kw= LEFT | kw= RIGHT | kw= FULL ) (kw= OUTER )? ) | kw= CROSS | kw= STRAIGHT_JOIN )?
            int alt142=5;
            switch ( input.LA(1) ) {
                case INNER:
                    {
                    alt142=1;
                    }
                    break;
                case RIGHT:
                case FULL:
                case LEFT:
                    {
                    alt142=2;
                    }
                    break;
                case CROSS:
                    {
                    alt142=3;
                    }
                    break;
                case STRAIGHT_JOIN:
                    {
                    alt142=4;
                    }
                    break;
            }

            switch (alt142) {
                case 1 :
                    // InternalSqlParser.g:7618:2: kw= INNER
                    {
                    kw=(Token)match(input,INNER,FOLLOW_101); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getJoinTypeAccess().getINNERKeyword_1_0()); 
                          
                    }

                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:7624:6: ( (kw= LEFT | kw= RIGHT | kw= FULL ) (kw= OUTER )? )
                    {
                    // InternalSqlParser.g:7624:6: ( (kw= LEFT | kw= RIGHT | kw= FULL ) (kw= OUTER )? )
                    // InternalSqlParser.g:7624:7: (kw= LEFT | kw= RIGHT | kw= FULL ) (kw= OUTER )?
                    {
                    // InternalSqlParser.g:7624:7: (kw= LEFT | kw= RIGHT | kw= FULL )
                    int alt140=3;
                    switch ( input.LA(1) ) {
                    case LEFT:
                        {
                        alt140=1;
                        }
                        break;
                    case RIGHT:
                        {
                        alt140=2;
                        }
                        break;
                    case FULL:
                        {
                        alt140=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 140, 0, input);

                        throw nvae;
                    }

                    switch (alt140) {
                        case 1 :
                            // InternalSqlParser.g:7625:2: kw= LEFT
                            {
                            kw=(Token)match(input,LEFT,FOLLOW_102); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                      current.merge(kw);
                                      newLeafNode(kw, grammarAccess.getJoinTypeAccess().getLEFTKeyword_1_1_0_0()); 
                                  
                            }

                            }
                            break;
                        case 2 :
                            // InternalSqlParser.g:7632:2: kw= RIGHT
                            {
                            kw=(Token)match(input,RIGHT,FOLLOW_102); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                      current.merge(kw);
                                      newLeafNode(kw, grammarAccess.getJoinTypeAccess().getRIGHTKeyword_1_1_0_1()); 
                                  
                            }

                            }
                            break;
                        case 3 :
                            // InternalSqlParser.g:7639:2: kw= FULL
                            {
                            kw=(Token)match(input,FULL,FOLLOW_102); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                      current.merge(kw);
                                      newLeafNode(kw, grammarAccess.getJoinTypeAccess().getFULLKeyword_1_1_0_2()); 
                                  
                            }

                            }
                            break;

                    }

                    // InternalSqlParser.g:7644:2: (kw= OUTER )?
                    int alt141=2;
                    int LA141_0 = input.LA(1);

                    if ( (LA141_0==OUTER) ) {
                        alt141=1;
                    }
                    switch (alt141) {
                        case 1 :
                            // InternalSqlParser.g:7645:2: kw= OUTER
                            {
                            kw=(Token)match(input,OUTER,FOLLOW_101); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                      current.merge(kw);
                                      newLeafNode(kw, grammarAccess.getJoinTypeAccess().getOUTERKeyword_1_1_1()); 
                                  
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSqlParser.g:7652:2: kw= CROSS
                    {
                    kw=(Token)match(input,CROSS,FOLLOW_101); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getJoinTypeAccess().getCROSSKeyword_1_2()); 
                          
                    }

                    }
                    break;
                case 4 :
                    // InternalSqlParser.g:7659:2: kw= STRAIGHT_JOIN
                    {
                    kw=(Token)match(input,STRAIGHT_JOIN,FOLLOW_101); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getJoinTypeAccess().getSTRAIGHT_JOINKeyword_1_3()); 
                          
                    }

                    }
                    break;

            }

            kw=(Token)match(input,JOIN,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current.merge(kw);
                      newLeafNode(kw, grammarAccess.getJoinTypeAccess().getJOINKeyword_2()); 
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
                  
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJoinType"


    // $ANTLR start "entryRuleDBID"
    // InternalSqlParser.g:7678:1: entryRuleDBID returns [String current=null] : iv_ruleDBID= ruleDBID EOF ;
    public final String entryRuleDBID() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDBID = null;


        try {
            // InternalSqlParser.g:7679:1: (iv_ruleDBID= ruleDBID EOF )
            // InternalSqlParser.g:7680:2: iv_ruleDBID= ruleDBID EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDBIDRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDBID=ruleDBID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDBID.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDBID"


    // $ANTLR start "ruleDBID"
    // InternalSqlParser.g:7687:1: ruleDBID returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | this_DBNAME_1= RULE_DBNAME | this_STRING_2= RULE_STRING ) ;
    public final AntlrDatatypeRuleToken ruleDBID() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token this_DBNAME_1=null;
        Token this_STRING_2=null;

         enterRule(); 
            
        try {
            // InternalSqlParser.g:7691:6: ( (this_ID_0= RULE_ID | this_DBNAME_1= RULE_DBNAME | this_STRING_2= RULE_STRING ) )
            // InternalSqlParser.g:7692:1: (this_ID_0= RULE_ID | this_DBNAME_1= RULE_DBNAME | this_STRING_2= RULE_STRING )
            {
            // InternalSqlParser.g:7692:1: (this_ID_0= RULE_ID | this_DBNAME_1= RULE_DBNAME | this_STRING_2= RULE_STRING )
            int alt143=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt143=1;
                }
                break;
            case RULE_DBNAME:
                {
                alt143=2;
                }
                break;
            case RULE_STRING:
                {
                alt143=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 143, 0, input);

                throw nvae;
            }

            switch (alt143) {
                case 1 :
                    // InternalSqlParser.g:7692:6: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      		current.merge(this_ID_0);
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                          newLeafNode(this_ID_0, grammarAccess.getDBIDAccess().getIDTerminalRuleCall_0()); 
                          
                    }

                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:7700:10: this_DBNAME_1= RULE_DBNAME
                    {
                    this_DBNAME_1=(Token)match(input,RULE_DBNAME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      		current.merge(this_DBNAME_1);
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                          newLeafNode(this_DBNAME_1, grammarAccess.getDBIDAccess().getDBNAMETerminalRuleCall_1()); 
                          
                    }

                    }
                    break;
                case 3 :
                    // InternalSqlParser.g:7708:10: this_STRING_2= RULE_STRING
                    {
                    this_STRING_2=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      		current.merge(this_STRING_2);
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                          newLeafNode(this_STRING_2, grammarAccess.getDBIDAccess().getSTRINGTerminalRuleCall_2()); 
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
                  
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDBID"


    // $ANTLR start "entryRuleStringOperand"
    // InternalSqlParser.g:7723:1: entryRuleStringOperand returns [String current=null] : iv_ruleStringOperand= ruleStringOperand EOF ;
    public final String entryRuleStringOperand() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleStringOperand = null;


        try {
            // InternalSqlParser.g:7724:1: (iv_ruleStringOperand= ruleStringOperand EOF )
            // InternalSqlParser.g:7725:2: iv_ruleStringOperand= ruleStringOperand EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStringOperandRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleStringOperand=ruleStringOperand();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleStringOperand.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStringOperand"


    // $ANTLR start "ruleStringOperand"
    // InternalSqlParser.g:7732:1: ruleStringOperand returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_STRING__0= RULE_STRING_ ;
    public final AntlrDatatypeRuleToken ruleStringOperand() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_STRING__0=null;

         enterRule(); 
            
        try {
            // InternalSqlParser.g:7736:6: (this_STRING__0= RULE_STRING_ )
            // InternalSqlParser.g:7737:5: this_STRING__0= RULE_STRING_
            {
            this_STRING__0=(Token)match(input,RULE_STRING_,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_STRING__0);
                  
            }
            if ( state.backtracking==0 ) {
               
                  newLeafNode(this_STRING__0, grammarAccess.getStringOperandAccess().getSTRING_TerminalRuleCall()); 
                  
            }

            }

            if ( state.backtracking==0 ) {
               leaveRule();
                  
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStringOperand"


    // $ANTLR start "entryRuleFNAME"
    // InternalSqlParser.g:7752:1: entryRuleFNAME returns [String current=null] : iv_ruleFNAME= ruleFNAME EOF ;
    public final String entryRuleFNAME() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleFNAME = null;


        try {
            // InternalSqlParser.g:7753:1: (iv_ruleFNAME= ruleFNAME EOF )
            // InternalSqlParser.g:7754:2: iv_ruleFNAME= ruleFNAME EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFNAMERule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleFNAME=ruleFNAME();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFNAME.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFNAME"


    // $ANTLR start "ruleFNAME"
    // InternalSqlParser.g:7761:1: ruleFNAME returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID kw= LeftParenthesis ) ;
    public final AntlrDatatypeRuleToken ruleFNAME() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;

         enterRule(); 
            
        try {
            // InternalSqlParser.g:7765:6: ( (this_ID_0= RULE_ID kw= LeftParenthesis ) )
            // InternalSqlParser.g:7766:1: (this_ID_0= RULE_ID kw= LeftParenthesis )
            {
            // InternalSqlParser.g:7766:1: (this_ID_0= RULE_ID kw= LeftParenthesis )
            // InternalSqlParser.g:7766:6: this_ID_0= RULE_ID kw= LeftParenthesis
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_ID_0);
                  
            }
            if ( state.backtracking==0 ) {
               
                  newLeafNode(this_ID_0, grammarAccess.getFNAMEAccess().getIDTerminalRuleCall_0()); 
                  
            }
            kw=(Token)match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current.merge(kw);
                      newLeafNode(kw, grammarAccess.getFNAMEAccess().getLeftParenthesisKeyword_1()); 
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
                  
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFNAME"


    // $ANTLR start "entryRuleUnsignedValue"
    // InternalSqlParser.g:7789:1: entryRuleUnsignedValue returns [EObject current=null] : iv_ruleUnsignedValue= ruleUnsignedValue EOF ;
    public final EObject entryRuleUnsignedValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnsignedValue = null;


        try {
            // InternalSqlParser.g:7790:2: (iv_ruleUnsignedValue= ruleUnsignedValue EOF )
            // InternalSqlParser.g:7791:2: iv_ruleUnsignedValue= ruleUnsignedValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnsignedValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUnsignedValue=ruleUnsignedValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUnsignedValue; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnsignedValue"


    // $ANTLR start "ruleUnsignedValue"
    // InternalSqlParser.g:7798:1: ruleUnsignedValue returns [EObject current=null] : ( (lv_integer_0_0= RULE_UNSIGNED ) ) ;
    public final EObject ruleUnsignedValue() throws RecognitionException {
        EObject current = null;

        Token lv_integer_0_0=null;

         enterRule(); 
            
        try {
            // InternalSqlParser.g:7801:28: ( ( (lv_integer_0_0= RULE_UNSIGNED ) ) )
            // InternalSqlParser.g:7802:1: ( (lv_integer_0_0= RULE_UNSIGNED ) )
            {
            // InternalSqlParser.g:7802:1: ( (lv_integer_0_0= RULE_UNSIGNED ) )
            // InternalSqlParser.g:7803:1: (lv_integer_0_0= RULE_UNSIGNED )
            {
            // InternalSqlParser.g:7803:1: (lv_integer_0_0= RULE_UNSIGNED )
            // InternalSqlParser.g:7804:3: lv_integer_0_0= RULE_UNSIGNED
            {
            lv_integer_0_0=(Token)match(input,RULE_UNSIGNED,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_integer_0_0, grammarAccess.getUnsignedValueAccess().getIntegerUNSIGNEDTerminalRuleCall_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getUnsignedValueRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"integer",
                      		lv_integer_0_0, 
                      		"com.jaspersoft.studio.data.Sql.UNSIGNED");
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnsignedValue"


    // $ANTLR start "ruleXFunction"
    // InternalSqlParser.g:7828:1: ruleXFunction returns [Enumerator current=null] : ( (enumLiteral_0= IN_1 ) | (enumLiteral_1= NOTIN ) | (enumLiteral_2= EQUAL ) | (enumLiteral_3= NOTEQUAL ) | (enumLiteral_4= LESS ) | (enumLiteral_5= LESS_1 ) | (enumLiteral_6= GREATER_1 ) | (enumLiteral_7= GREATER ) | (enumLiteral_8= BETWEEN_1 ) | (enumLiteral_9= BETWEEN_4 ) | (enumLiteral_10= BETWEEN_3 ) | (enumLiteral_11= BETWEEN_2 ) ) ;
    public final Enumerator ruleXFunction() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;
        Token enumLiteral_6=null;
        Token enumLiteral_7=null;
        Token enumLiteral_8=null;
        Token enumLiteral_9=null;
        Token enumLiteral_10=null;
        Token enumLiteral_11=null;

         enterRule(); 
        try {
            // InternalSqlParser.g:7830:28: ( ( (enumLiteral_0= IN_1 ) | (enumLiteral_1= NOTIN ) | (enumLiteral_2= EQUAL ) | (enumLiteral_3= NOTEQUAL ) | (enumLiteral_4= LESS ) | (enumLiteral_5= LESS_1 ) | (enumLiteral_6= GREATER_1 ) | (enumLiteral_7= GREATER ) | (enumLiteral_8= BETWEEN_1 ) | (enumLiteral_9= BETWEEN_4 ) | (enumLiteral_10= BETWEEN_3 ) | (enumLiteral_11= BETWEEN_2 ) ) )
            // InternalSqlParser.g:7831:1: ( (enumLiteral_0= IN_1 ) | (enumLiteral_1= NOTIN ) | (enumLiteral_2= EQUAL ) | (enumLiteral_3= NOTEQUAL ) | (enumLiteral_4= LESS ) | (enumLiteral_5= LESS_1 ) | (enumLiteral_6= GREATER_1 ) | (enumLiteral_7= GREATER ) | (enumLiteral_8= BETWEEN_1 ) | (enumLiteral_9= BETWEEN_4 ) | (enumLiteral_10= BETWEEN_3 ) | (enumLiteral_11= BETWEEN_2 ) )
            {
            // InternalSqlParser.g:7831:1: ( (enumLiteral_0= IN_1 ) | (enumLiteral_1= NOTIN ) | (enumLiteral_2= EQUAL ) | (enumLiteral_3= NOTEQUAL ) | (enumLiteral_4= LESS ) | (enumLiteral_5= LESS_1 ) | (enumLiteral_6= GREATER_1 ) | (enumLiteral_7= GREATER ) | (enumLiteral_8= BETWEEN_1 ) | (enumLiteral_9= BETWEEN_4 ) | (enumLiteral_10= BETWEEN_3 ) | (enumLiteral_11= BETWEEN_2 ) )
            int alt144=12;
            switch ( input.LA(1) ) {
            case IN_1:
                {
                alt144=1;
                }
                break;
            case NOTIN:
                {
                alt144=2;
                }
                break;
            case EQUAL:
                {
                alt144=3;
                }
                break;
            case NOTEQUAL:
                {
                alt144=4;
                }
                break;
            case LESS:
                {
                alt144=5;
                }
                break;
            case LESS_1:
                {
                alt144=6;
                }
                break;
            case GREATER_1:
                {
                alt144=7;
                }
                break;
            case GREATER:
                {
                alt144=8;
                }
                break;
            case BETWEEN_1:
                {
                alt144=9;
                }
                break;
            case BETWEEN_4:
                {
                alt144=10;
                }
                break;
            case BETWEEN_3:
                {
                alt144=11;
                }
                break;
            case BETWEEN_2:
                {
                alt144=12;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 144, 0, input);

                throw nvae;
            }

            switch (alt144) {
                case 1 :
                    // InternalSqlParser.g:7831:2: (enumLiteral_0= IN_1 )
                    {
                    // InternalSqlParser.g:7831:2: (enumLiteral_0= IN_1 )
                    // InternalSqlParser.g:7831:7: enumLiteral_0= IN_1
                    {
                    enumLiteral_0=(Token)match(input,IN_1,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getXFunctionAccess().getXinEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_0, grammarAccess.getXFunctionAccess().getXinEnumLiteralDeclaration_0()); 
                          
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:7837:6: (enumLiteral_1= NOTIN )
                    {
                    // InternalSqlParser.g:7837:6: (enumLiteral_1= NOTIN )
                    // InternalSqlParser.g:7837:11: enumLiteral_1= NOTIN
                    {
                    enumLiteral_1=(Token)match(input,NOTIN,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getXFunctionAccess().getXnotinEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_1, grammarAccess.getXFunctionAccess().getXnotinEnumLiteralDeclaration_1()); 
                          
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSqlParser.g:7843:6: (enumLiteral_2= EQUAL )
                    {
                    // InternalSqlParser.g:7843:6: (enumLiteral_2= EQUAL )
                    // InternalSqlParser.g:7843:11: enumLiteral_2= EQUAL
                    {
                    enumLiteral_2=(Token)match(input,EQUAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getXFunctionAccess().getXeqEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_2, grammarAccess.getXFunctionAccess().getXeqEnumLiteralDeclaration_2()); 
                          
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSqlParser.g:7849:6: (enumLiteral_3= NOTEQUAL )
                    {
                    // InternalSqlParser.g:7849:6: (enumLiteral_3= NOTEQUAL )
                    // InternalSqlParser.g:7849:11: enumLiteral_3= NOTEQUAL
                    {
                    enumLiteral_3=(Token)match(input,NOTEQUAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getXFunctionAccess().getXnoteqEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_3, grammarAccess.getXFunctionAccess().getXnoteqEnumLiteralDeclaration_3()); 
                          
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSqlParser.g:7855:6: (enumLiteral_4= LESS )
                    {
                    // InternalSqlParser.g:7855:6: (enumLiteral_4= LESS )
                    // InternalSqlParser.g:7855:11: enumLiteral_4= LESS
                    {
                    enumLiteral_4=(Token)match(input,LESS,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getXFunctionAccess().getXlsEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_4, grammarAccess.getXFunctionAccess().getXlsEnumLiteralDeclaration_4()); 
                          
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSqlParser.g:7861:6: (enumLiteral_5= LESS_1 )
                    {
                    // InternalSqlParser.g:7861:6: (enumLiteral_5= LESS_1 )
                    // InternalSqlParser.g:7861:11: enumLiteral_5= LESS_1
                    {
                    enumLiteral_5=(Token)match(input,LESS_1,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getXFunctionAccess().getXlsrEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_5, grammarAccess.getXFunctionAccess().getXlsrEnumLiteralDeclaration_5()); 
                          
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSqlParser.g:7867:6: (enumLiteral_6= GREATER_1 )
                    {
                    // InternalSqlParser.g:7867:6: (enumLiteral_6= GREATER_1 )
                    // InternalSqlParser.g:7867:11: enumLiteral_6= GREATER_1
                    {
                    enumLiteral_6=(Token)match(input,GREATER_1,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getXFunctionAccess().getXgtlEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_6, grammarAccess.getXFunctionAccess().getXgtlEnumLiteralDeclaration_6()); 
                          
                    }

                    }


                    }
                    break;
                case 8 :
                    // InternalSqlParser.g:7873:6: (enumLiteral_7= GREATER )
                    {
                    // InternalSqlParser.g:7873:6: (enumLiteral_7= GREATER )
                    // InternalSqlParser.g:7873:11: enumLiteral_7= GREATER
                    {
                    enumLiteral_7=(Token)match(input,GREATER,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getXFunctionAccess().getXgtEnumLiteralDeclaration_7().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_7, grammarAccess.getXFunctionAccess().getXgtEnumLiteralDeclaration_7()); 
                          
                    }

                    }


                    }
                    break;
                case 9 :
                    // InternalSqlParser.g:7879:6: (enumLiteral_8= BETWEEN_1 )
                    {
                    // InternalSqlParser.g:7879:6: (enumLiteral_8= BETWEEN_1 )
                    // InternalSqlParser.g:7879:11: enumLiteral_8= BETWEEN_1
                    {
                    enumLiteral_8=(Token)match(input,BETWEEN_1,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getXFunctionAccess().getXbwnEnumLiteralDeclaration_8().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_8, grammarAccess.getXFunctionAccess().getXbwnEnumLiteralDeclaration_8()); 
                          
                    }

                    }


                    }
                    break;
                case 10 :
                    // InternalSqlParser.g:7885:6: (enumLiteral_9= BETWEEN_4 )
                    {
                    // InternalSqlParser.g:7885:6: (enumLiteral_9= BETWEEN_4 )
                    // InternalSqlParser.g:7885:11: enumLiteral_9= BETWEEN_4
                    {
                    enumLiteral_9=(Token)match(input,BETWEEN_4,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getXFunctionAccess().getXbwncEnumLiteralDeclaration_9().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_9, grammarAccess.getXFunctionAccess().getXbwncEnumLiteralDeclaration_9()); 
                          
                    }

                    }


                    }
                    break;
                case 11 :
                    // InternalSqlParser.g:7891:6: (enumLiteral_10= BETWEEN_3 )
                    {
                    // InternalSqlParser.g:7891:6: (enumLiteral_10= BETWEEN_3 )
                    // InternalSqlParser.g:7891:11: enumLiteral_10= BETWEEN_3
                    {
                    enumLiteral_10=(Token)match(input,BETWEEN_3,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getXFunctionAccess().getXbwnlEnumLiteralDeclaration_10().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_10, grammarAccess.getXFunctionAccess().getXbwnlEnumLiteralDeclaration_10()); 
                          
                    }

                    }


                    }
                    break;
                case 12 :
                    // InternalSqlParser.g:7897:6: (enumLiteral_11= BETWEEN_2 )
                    {
                    // InternalSqlParser.g:7897:6: (enumLiteral_11= BETWEEN_2 )
                    // InternalSqlParser.g:7897:11: enumLiteral_11= BETWEEN_2
                    {
                    enumLiteral_11=(Token)match(input,BETWEEN_2,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getXFunctionAccess().getXbwnrEnumLiteralDeclaration_11().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_11, grammarAccess.getXFunctionAccess().getXbwnrEnumLiteralDeclaration_11()); 
                          
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXFunction"


    // $ANTLR start "ruleEXTRACT_VALUES"
    // InternalSqlParser.g:7907:1: ruleEXTRACT_VALUES returns [Enumerator current=null] : ( (enumLiteral_0= MICROSECOND ) | (enumLiteral_1= SECOND ) | (enumLiteral_2= MINUTE ) | (enumLiteral_3= HOUR ) | (enumLiteral_4= DAY ) | (enumLiteral_5= WEEK ) | (enumLiteral_6= MONTH ) | (enumLiteral_7= QUARTER ) | (enumLiteral_8= YEAR ) | (enumLiteral_9= SECOND_MICROSECOND ) | (enumLiteral_10= MINUTE_MICROSECOND ) | (enumLiteral_11= MINUTE_SECOND ) | (enumLiteral_12= HOUR_MICROSECOND ) | (enumLiteral_13= HOUR_SECOND ) | (enumLiteral_14= HOUR_MINUTE ) | (enumLiteral_15= DAY_MICROSECOND ) | (enumLiteral_16= DAY_SECOND ) | (enumLiteral_17= DAY_MINUTE ) | (enumLiteral_18= DAY_HOUR ) | (enumLiteral_19= YEAR_MONTH ) ) ;
    public final Enumerator ruleEXTRACT_VALUES() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;
        Token enumLiteral_6=null;
        Token enumLiteral_7=null;
        Token enumLiteral_8=null;
        Token enumLiteral_9=null;
        Token enumLiteral_10=null;
        Token enumLiteral_11=null;
        Token enumLiteral_12=null;
        Token enumLiteral_13=null;
        Token enumLiteral_14=null;
        Token enumLiteral_15=null;
        Token enumLiteral_16=null;
        Token enumLiteral_17=null;
        Token enumLiteral_18=null;
        Token enumLiteral_19=null;

         enterRule(); 
        try {
            // InternalSqlParser.g:7909:28: ( ( (enumLiteral_0= MICROSECOND ) | (enumLiteral_1= SECOND ) | (enumLiteral_2= MINUTE ) | (enumLiteral_3= HOUR ) | (enumLiteral_4= DAY ) | (enumLiteral_5= WEEK ) | (enumLiteral_6= MONTH ) | (enumLiteral_7= QUARTER ) | (enumLiteral_8= YEAR ) | (enumLiteral_9= SECOND_MICROSECOND ) | (enumLiteral_10= MINUTE_MICROSECOND ) | (enumLiteral_11= MINUTE_SECOND ) | (enumLiteral_12= HOUR_MICROSECOND ) | (enumLiteral_13= HOUR_SECOND ) | (enumLiteral_14= HOUR_MINUTE ) | (enumLiteral_15= DAY_MICROSECOND ) | (enumLiteral_16= DAY_SECOND ) | (enumLiteral_17= DAY_MINUTE ) | (enumLiteral_18= DAY_HOUR ) | (enumLiteral_19= YEAR_MONTH ) ) )
            // InternalSqlParser.g:7910:1: ( (enumLiteral_0= MICROSECOND ) | (enumLiteral_1= SECOND ) | (enumLiteral_2= MINUTE ) | (enumLiteral_3= HOUR ) | (enumLiteral_4= DAY ) | (enumLiteral_5= WEEK ) | (enumLiteral_6= MONTH ) | (enumLiteral_7= QUARTER ) | (enumLiteral_8= YEAR ) | (enumLiteral_9= SECOND_MICROSECOND ) | (enumLiteral_10= MINUTE_MICROSECOND ) | (enumLiteral_11= MINUTE_SECOND ) | (enumLiteral_12= HOUR_MICROSECOND ) | (enumLiteral_13= HOUR_SECOND ) | (enumLiteral_14= HOUR_MINUTE ) | (enumLiteral_15= DAY_MICROSECOND ) | (enumLiteral_16= DAY_SECOND ) | (enumLiteral_17= DAY_MINUTE ) | (enumLiteral_18= DAY_HOUR ) | (enumLiteral_19= YEAR_MONTH ) )
            {
            // InternalSqlParser.g:7910:1: ( (enumLiteral_0= MICROSECOND ) | (enumLiteral_1= SECOND ) | (enumLiteral_2= MINUTE ) | (enumLiteral_3= HOUR ) | (enumLiteral_4= DAY ) | (enumLiteral_5= WEEK ) | (enumLiteral_6= MONTH ) | (enumLiteral_7= QUARTER ) | (enumLiteral_8= YEAR ) | (enumLiteral_9= SECOND_MICROSECOND ) | (enumLiteral_10= MINUTE_MICROSECOND ) | (enumLiteral_11= MINUTE_SECOND ) | (enumLiteral_12= HOUR_MICROSECOND ) | (enumLiteral_13= HOUR_SECOND ) | (enumLiteral_14= HOUR_MINUTE ) | (enumLiteral_15= DAY_MICROSECOND ) | (enumLiteral_16= DAY_SECOND ) | (enumLiteral_17= DAY_MINUTE ) | (enumLiteral_18= DAY_HOUR ) | (enumLiteral_19= YEAR_MONTH ) )
            int alt145=20;
            switch ( input.LA(1) ) {
            case MICROSECOND:
                {
                alt145=1;
                }
                break;
            case SECOND:
                {
                alt145=2;
                }
                break;
            case MINUTE:
                {
                alt145=3;
                }
                break;
            case HOUR:
                {
                alt145=4;
                }
                break;
            case DAY:
                {
                alt145=5;
                }
                break;
            case WEEK:
                {
                alt145=6;
                }
                break;
            case MONTH:
                {
                alt145=7;
                }
                break;
            case QUARTER:
                {
                alt145=8;
                }
                break;
            case YEAR:
                {
                alt145=9;
                }
                break;
            case SECOND_MICROSECOND:
                {
                alt145=10;
                }
                break;
            case MINUTE_MICROSECOND:
                {
                alt145=11;
                }
                break;
            case MINUTE_SECOND:
                {
                alt145=12;
                }
                break;
            case HOUR_MICROSECOND:
                {
                alt145=13;
                }
                break;
            case HOUR_SECOND:
                {
                alt145=14;
                }
                break;
            case HOUR_MINUTE:
                {
                alt145=15;
                }
                break;
            case DAY_MICROSECOND:
                {
                alt145=16;
                }
                break;
            case DAY_SECOND:
                {
                alt145=17;
                }
                break;
            case DAY_MINUTE:
                {
                alt145=18;
                }
                break;
            case DAY_HOUR:
                {
                alt145=19;
                }
                break;
            case YEAR_MONTH:
                {
                alt145=20;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 145, 0, input);

                throw nvae;
            }

            switch (alt145) {
                case 1 :
                    // InternalSqlParser.g:7910:2: (enumLiteral_0= MICROSECOND )
                    {
                    // InternalSqlParser.g:7910:2: (enumLiteral_0= MICROSECOND )
                    // InternalSqlParser.g:7910:7: enumLiteral_0= MICROSECOND
                    {
                    enumLiteral_0=(Token)match(input,MICROSECOND,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getEXTRACT_VALUESAccess().getMsEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_0, grammarAccess.getEXTRACT_VALUESAccess().getMsEnumLiteralDeclaration_0()); 
                          
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSqlParser.g:7916:6: (enumLiteral_1= SECOND )
                    {
                    // InternalSqlParser.g:7916:6: (enumLiteral_1= SECOND )
                    // InternalSqlParser.g:7916:11: enumLiteral_1= SECOND
                    {
                    enumLiteral_1=(Token)match(input,SECOND,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getEXTRACT_VALUESAccess().getSEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_1, grammarAccess.getEXTRACT_VALUESAccess().getSEnumLiteralDeclaration_1()); 
                          
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSqlParser.g:7922:6: (enumLiteral_2= MINUTE )
                    {
                    // InternalSqlParser.g:7922:6: (enumLiteral_2= MINUTE )
                    // InternalSqlParser.g:7922:11: enumLiteral_2= MINUTE
                    {
                    enumLiteral_2=(Token)match(input,MINUTE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getEXTRACT_VALUESAccess().getMEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_2, grammarAccess.getEXTRACT_VALUESAccess().getMEnumLiteralDeclaration_2()); 
                          
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSqlParser.g:7928:6: (enumLiteral_3= HOUR )
                    {
                    // InternalSqlParser.g:7928:6: (enumLiteral_3= HOUR )
                    // InternalSqlParser.g:7928:11: enumLiteral_3= HOUR
                    {
                    enumLiteral_3=(Token)match(input,HOUR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getEXTRACT_VALUESAccess().getHEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_3, grammarAccess.getEXTRACT_VALUESAccess().getHEnumLiteralDeclaration_3()); 
                          
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSqlParser.g:7934:6: (enumLiteral_4= DAY )
                    {
                    // InternalSqlParser.g:7934:6: (enumLiteral_4= DAY )
                    // InternalSqlParser.g:7934:11: enumLiteral_4= DAY
                    {
                    enumLiteral_4=(Token)match(input,DAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getEXTRACT_VALUESAccess().getDayEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_4, grammarAccess.getEXTRACT_VALUESAccess().getDayEnumLiteralDeclaration_4()); 
                          
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSqlParser.g:7940:6: (enumLiteral_5= WEEK )
                    {
                    // InternalSqlParser.g:7940:6: (enumLiteral_5= WEEK )
                    // InternalSqlParser.g:7940:11: enumLiteral_5= WEEK
                    {
                    enumLiteral_5=(Token)match(input,WEEK,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getEXTRACT_VALUESAccess().getWeekEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_5, grammarAccess.getEXTRACT_VALUESAccess().getWeekEnumLiteralDeclaration_5()); 
                          
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSqlParser.g:7946:6: (enumLiteral_6= MONTH )
                    {
                    // InternalSqlParser.g:7946:6: (enumLiteral_6= MONTH )
                    // InternalSqlParser.g:7946:11: enumLiteral_6= MONTH
                    {
                    enumLiteral_6=(Token)match(input,MONTH,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getEXTRACT_VALUESAccess().getMonthEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_6, grammarAccess.getEXTRACT_VALUESAccess().getMonthEnumLiteralDeclaration_6()); 
                          
                    }

                    }


                    }
                    break;
                case 8 :
                    // InternalSqlParser.g:7952:6: (enumLiteral_7= QUARTER )
                    {
                    // InternalSqlParser.g:7952:6: (enumLiteral_7= QUARTER )
                    // InternalSqlParser.g:7952:11: enumLiteral_7= QUARTER
                    {
                    enumLiteral_7=(Token)match(input,QUARTER,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getEXTRACT_VALUESAccess().getQuartEnumLiteralDeclaration_7().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_7, grammarAccess.getEXTRACT_VALUESAccess().getQuartEnumLiteralDeclaration_7()); 
                          
                    }

                    }


                    }
                    break;
                case 9 :
                    // InternalSqlParser.g:7958:6: (enumLiteral_8= YEAR )
                    {
                    // InternalSqlParser.g:7958:6: (enumLiteral_8= YEAR )
                    // InternalSqlParser.g:7958:11: enumLiteral_8= YEAR
                    {
                    enumLiteral_8=(Token)match(input,YEAR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getEXTRACT_VALUESAccess().getYearEnumLiteralDeclaration_8().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_8, grammarAccess.getEXTRACT_VALUESAccess().getYearEnumLiteralDeclaration_8()); 
                          
                    }

                    }


                    }
                    break;
                case 10 :
                    // InternalSqlParser.g:7964:6: (enumLiteral_9= SECOND_MICROSECOND )
                    {
                    // InternalSqlParser.g:7964:6: (enumLiteral_9= SECOND_MICROSECOND )
                    // InternalSqlParser.g:7964:11: enumLiteral_9= SECOND_MICROSECOND
                    {
                    enumLiteral_9=(Token)match(input,SECOND_MICROSECOND,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getEXTRACT_VALUESAccess().getMicrosEnumLiteralDeclaration_9().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_9, grammarAccess.getEXTRACT_VALUESAccess().getMicrosEnumLiteralDeclaration_9()); 
                          
                    }

                    }


                    }
                    break;
                case 11 :
                    // InternalSqlParser.g:7970:6: (enumLiteral_10= MINUTE_MICROSECOND )
                    {
                    // InternalSqlParser.g:7970:6: (enumLiteral_10= MINUTE_MICROSECOND )
                    // InternalSqlParser.g:7970:11: enumLiteral_10= MINUTE_MICROSECOND
                    {
                    enumLiteral_10=(Token)match(input,MINUTE_MICROSECOND,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getEXTRACT_VALUESAccess().getMinMicroEnumLiteralDeclaration_10().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_10, grammarAccess.getEXTRACT_VALUESAccess().getMinMicroEnumLiteralDeclaration_10()); 
                          
                    }

                    }


                    }
                    break;
                case 12 :
                    // InternalSqlParser.g:7976:6: (enumLiteral_11= MINUTE_SECOND )
                    {
                    // InternalSqlParser.g:7976:6: (enumLiteral_11= MINUTE_SECOND )
                    // InternalSqlParser.g:7976:11: enumLiteral_11= MINUTE_SECOND
                    {
                    enumLiteral_11=(Token)match(input,MINUTE_SECOND,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getEXTRACT_VALUESAccess().getMinSecEnumLiteralDeclaration_11().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_11, grammarAccess.getEXTRACT_VALUESAccess().getMinSecEnumLiteralDeclaration_11()); 
                          
                    }

                    }


                    }
                    break;
                case 13 :
                    // InternalSqlParser.g:7982:6: (enumLiteral_12= HOUR_MICROSECOND )
                    {
                    // InternalSqlParser.g:7982:6: (enumLiteral_12= HOUR_MICROSECOND )
                    // InternalSqlParser.g:7982:11: enumLiteral_12= HOUR_MICROSECOND
                    {
                    enumLiteral_12=(Token)match(input,HOUR_MICROSECOND,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getEXTRACT_VALUESAccess().getHmsEnumLiteralDeclaration_12().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_12, grammarAccess.getEXTRACT_VALUESAccess().getHmsEnumLiteralDeclaration_12()); 
                          
                    }

                    }


                    }
                    break;
                case 14 :
                    // InternalSqlParser.g:7988:6: (enumLiteral_13= HOUR_SECOND )
                    {
                    // InternalSqlParser.g:7988:6: (enumLiteral_13= HOUR_SECOND )
                    // InternalSqlParser.g:7988:11: enumLiteral_13= HOUR_SECOND
                    {
                    enumLiteral_13=(Token)match(input,HOUR_SECOND,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getEXTRACT_VALUESAccess().getHsEnumLiteralDeclaration_13().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_13, grammarAccess.getEXTRACT_VALUESAccess().getHsEnumLiteralDeclaration_13()); 
                          
                    }

                    }


                    }
                    break;
                case 15 :
                    // InternalSqlParser.g:7994:6: (enumLiteral_14= HOUR_MINUTE )
                    {
                    // InternalSqlParser.g:7994:6: (enumLiteral_14= HOUR_MINUTE )
                    // InternalSqlParser.g:7994:11: enumLiteral_14= HOUR_MINUTE
                    {
                    enumLiteral_14=(Token)match(input,HOUR_MINUTE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getEXTRACT_VALUESAccess().getHminEnumLiteralDeclaration_14().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_14, grammarAccess.getEXTRACT_VALUESAccess().getHminEnumLiteralDeclaration_14()); 
                          
                    }

                    }


                    }
                    break;
                case 16 :
                    // InternalSqlParser.g:8000:6: (enumLiteral_15= DAY_MICROSECOND )
                    {
                    // InternalSqlParser.g:8000:6: (enumLiteral_15= DAY_MICROSECOND )
                    // InternalSqlParser.g:8000:11: enumLiteral_15= DAY_MICROSECOND
                    {
                    enumLiteral_15=(Token)match(input,DAY_MICROSECOND,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getEXTRACT_VALUESAccess().getDmsEnumLiteralDeclaration_15().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_15, grammarAccess.getEXTRACT_VALUESAccess().getDmsEnumLiteralDeclaration_15()); 
                          
                    }

                    }


                    }
                    break;
                case 17 :
                    // InternalSqlParser.g:8006:6: (enumLiteral_16= DAY_SECOND )
                    {
                    // InternalSqlParser.g:8006:6: (enumLiteral_16= DAY_SECOND )
                    // InternalSqlParser.g:8006:11: enumLiteral_16= DAY_SECOND
                    {
                    enumLiteral_16=(Token)match(input,DAY_SECOND,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getEXTRACT_VALUESAccess().getDsEnumLiteralDeclaration_16().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_16, grammarAccess.getEXTRACT_VALUESAccess().getDsEnumLiteralDeclaration_16()); 
                          
                    }

                    }


                    }
                    break;
                case 18 :
                    // InternalSqlParser.g:8012:6: (enumLiteral_17= DAY_MINUTE )
                    {
                    // InternalSqlParser.g:8012:6: (enumLiteral_17= DAY_MINUTE )
                    // InternalSqlParser.g:8012:11: enumLiteral_17= DAY_MINUTE
                    {
                    enumLiteral_17=(Token)match(input,DAY_MINUTE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getEXTRACT_VALUESAccess().getDayminEnumLiteralDeclaration_17().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_17, grammarAccess.getEXTRACT_VALUESAccess().getDayminEnumLiteralDeclaration_17()); 
                          
                    }

                    }


                    }
                    break;
                case 19 :
                    // InternalSqlParser.g:8018:6: (enumLiteral_18= DAY_HOUR )
                    {
                    // InternalSqlParser.g:8018:6: (enumLiteral_18= DAY_HOUR )
                    // InternalSqlParser.g:8018:11: enumLiteral_18= DAY_HOUR
                    {
                    enumLiteral_18=(Token)match(input,DAY_HOUR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getEXTRACT_VALUESAccess().getDayhEnumLiteralDeclaration_18().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_18, grammarAccess.getEXTRACT_VALUESAccess().getDayhEnumLiteralDeclaration_18()); 
                          
                    }

                    }


                    }
                    break;
                case 20 :
                    // InternalSqlParser.g:8024:6: (enumLiteral_19= YEAR_MONTH )
                    {
                    // InternalSqlParser.g:8024:6: (enumLiteral_19= YEAR_MONTH )
                    // InternalSqlParser.g:8024:11: enumLiteral_19= YEAR_MONTH
                    {
                    enumLiteral_19=(Token)match(input,YEAR_MONTH,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getEXTRACT_VALUESAccess().getYearMonthEnumLiteralDeclaration_19().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_19, grammarAccess.getEXTRACT_VALUESAccess().getYearMonthEnumLiteralDeclaration_19()); 
                          
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
        	catch (RecognitionException re) { 
        	    recover(input,re); 
        	    appendSkippedTokens();
        	}
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEXTRACT_VALUES"

    // $ANTLR start synpred176_InternalSqlParser
    public final void synpred176_InternalSqlParser_fragment() throws RecognitionException {   
        EObject lv_wop_1_0 = null;


        // InternalSqlParser.g:7360:2: ( ( (lv_wop_1_0= ruleOperandGroup ) ) )
        // InternalSqlParser.g:7360:2: ( (lv_wop_1_0= ruleOperandGroup ) )
        {
        // InternalSqlParser.g:7360:2: ( (lv_wop_1_0= ruleOperandGroup ) )
        // InternalSqlParser.g:7361:1: (lv_wop_1_0= ruleOperandGroup )
        {
        // InternalSqlParser.g:7361:1: (lv_wop_1_0= ruleOperandGroup )
        // InternalSqlParser.g:7362:3: lv_wop_1_0= ruleOperandGroup
        {
        if ( state.backtracking==0 ) {
           
          	        newCompositeNode(grammarAccess.getSQLCASEAccess().getWopOperandGroupParserRuleCall_1_0_0()); 
          	    
        }
        pushFollow(FOLLOW_2);
        lv_wop_1_0=ruleOperandGroup();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred176_InternalSqlParser

    // $ANTLR start synpred177_InternalSqlParser
    public final void synpred177_InternalSqlParser_fragment() throws RecognitionException {   
        EObject lv_expr_2_0 = null;


        // InternalSqlParser.g:7379:6: ( ( (lv_expr_2_0= ruleFullExpression ) ) )
        // InternalSqlParser.g:7379:6: ( (lv_expr_2_0= ruleFullExpression ) )
        {
        // InternalSqlParser.g:7379:6: ( (lv_expr_2_0= ruleFullExpression ) )
        // InternalSqlParser.g:7380:1: (lv_expr_2_0= ruleFullExpression )
        {
        // InternalSqlParser.g:7380:1: (lv_expr_2_0= ruleFullExpression )
        // InternalSqlParser.g:7381:3: lv_expr_2_0= ruleFullExpression
        {
        if ( state.backtracking==0 ) {
           
          	        newCompositeNode(grammarAccess.getSQLCASEAccess().getExprFullExpressionParserRuleCall_1_1_0()); 
          	    
        }
        pushFollow(FOLLOW_2);
        lv_expr_2_0=ruleFullExpression();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred177_InternalSqlParser

    // $ANTLR start synpred180_InternalSqlParser
    public final void synpred180_InternalSqlParser_fragment() throws RecognitionException {   
        EObject lv_wop_1_0 = null;


        // InternalSqlParser.g:7506:2: ( ( (lv_wop_1_0= ruleOperandGroup ) ) )
        // InternalSqlParser.g:7506:2: ( (lv_wop_1_0= ruleOperandGroup ) )
        {
        // InternalSqlParser.g:7506:2: ( (lv_wop_1_0= ruleOperandGroup ) )
        // InternalSqlParser.g:7507:1: (lv_wop_1_0= ruleOperandGroup )
        {
        // InternalSqlParser.g:7507:1: (lv_wop_1_0= ruleOperandGroup )
        // InternalSqlParser.g:7508:3: lv_wop_1_0= ruleOperandGroup
        {
        if ( state.backtracking==0 ) {
           
          	        newCompositeNode(grammarAccess.getSqlCaseWhenAccess().getWopOperandGroupParserRuleCall_1_0_0()); 
          	    
        }
        pushFollow(FOLLOW_2);
        lv_wop_1_0=ruleOperandGroup();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred180_InternalSqlParser

    // Delegated rules

    public final boolean synpred176_InternalSqlParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred176_InternalSqlParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred180_InternalSqlParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred180_InternalSqlParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred177_InternalSqlParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred177_InternalSqlParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA78 dfa78 = new DFA78(this);
    protected DFA77 dfa77 = new DFA77(this);
    protected DFA134 dfa134 = new DFA134(this);
    protected DFA137 dfa137 = new DFA137(this);
    static final String dfa_1s = "\12\uffff";
    static final String dfa_2s = "\5\uffff\1\11\4\uffff";
    static final String dfa_3s = "\1\42\1\51\1\uffff\1\42\1\uffff\1\11\4\uffff";
    static final String dfa_4s = "\1\u008f\1\166\1\uffff\1\u008f\1\uffff\1\u0084\4\uffff";
    static final String dfa_5s = "\2\uffff\1\1\1\uffff\1\2\1\uffff\1\3\1\5\1\6\1\4";
    static final String dfa_6s = "\12\uffff}>";
    static final String[] dfa_7s = {
            "\1\4\6\uffff\1\10\11\uffff\1\4\22\uffff\1\4\11\uffff\1\2\23\uffff\1\1\5\uffff\1\6\5\uffff\1\7\5\uffff\1\3\13\uffff\1\4\1\5\1\uffff\3\4\4\uffff\4\4",
            "\1\10\106\uffff\1\7\5\uffff\1\2",
            "",
            "\1\2\6\uffff\1\2\4\uffff\1\4\4\uffff\1\2\22\uffff\1\2\11\uffff\1\2\23\uffff\1\2\5\uffff\1\2\5\uffff\1\2\5\uffff\1\2\13\uffff\2\2\1\uffff\3\2\4\uffff\4\2",
            "",
            "\1\11\10\uffff\1\11\14\uffff\1\4\4\uffff\1\11\3\uffff\1\11\1\4\1\11\1\uffff\1\11\7\uffff\2\11\1\uffff\4\11\2\uffff\1\11\3\uffff\2\11\1\uffff\1\11\5\uffff\1\11\1\uffff\1\11\1\uffff\1\11\1\4\6\uffff\1\11\2\uffff\1\11\4\uffff\1\11\5\uffff\1\4\4\uffff\1\4\1\uffff\3\4\2\uffff\2\4\1\uffff\1\11\2\4\1\uffff\1\11\1\4\1\11\1\4\1\uffff\4\4\3\uffff\1\11\1\4",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA78 extends DFA {

        public DFA78(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 78;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "3656:1: ( ( (lv_expgroup_0_0= ruleExpressionGroup ) ) | ( (lv_exp_1_0= ruleExpression ) ) | ( ( (lv_xexp_2_1= ruleXExpression | lv_xexp_2_2= ruleXExpression_ ) ) ) | ( (lv_notPrm_3_0= RULE_JRNPARAM ) ) | ( (lv_in_4_0= ruleInOperator ) ) | ( (lv_exists_5_0= ruleExistsOperator ) ) )";
        }
    }
    static final String dfa_8s = "\20\uffff";
    static final String dfa_9s = "\1\152\1\20\14\171\2\uffff";
    static final String dfa_10s = "\1\152\1\150\14\u0080\2\uffff";
    static final String dfa_11s = "\16\uffff\1\1\1\2";
    static final String dfa_12s = "\20\uffff}>";
    static final String[] dfa_13s = {
            "\1\1",
            "\1\13\5\uffff\1\15\1\5\1\14\1\10\3\uffff\1\12\1\11\21\uffff\1\4\1\7\1\3\22\uffff\1\6\42\uffff\1\2",
            "\1\16\6\uffff\1\17",
            "\1\16\6\uffff\1\17",
            "\1\16\6\uffff\1\17",
            "\1\16\6\uffff\1\17",
            "\1\16\6\uffff\1\17",
            "\1\16\6\uffff\1\17",
            "\1\16\6\uffff\1\17",
            "\1\16\6\uffff\1\17",
            "\1\16\6\uffff\1\17",
            "\1\16\6\uffff\1\17",
            "\1\16\6\uffff\1\17",
            "\1\16\6\uffff\1\17",
            "",
            ""
    };

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final char[] dfa_9 = DFA.unpackEncodedStringToUnsignedChars(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final short[] dfa_11 = DFA.unpackEncodedString(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[][] dfa_13 = unpackEncodedStringArray(dfa_13s);

    class DFA77 extends DFA {

        public DFA77(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 77;
            this.eot = dfa_8;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "3696:1: (lv_xexp_2_1= ruleXExpression | lv_xexp_2_2= ruleXExpression_ )";
        }
    }
    static final String dfa_14s = "\25\uffff";
    static final String dfa_15s = "\1\42\15\0\7\uffff";
    static final String dfa_16s = "\1\u008f\15\0\7\uffff";
    static final String dfa_17s = "\16\uffff\1\2\4\uffff\1\3\1\1";
    static final String dfa_18s = "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\7\uffff}>";
    static final String[] dfa_19s = {
            "\1\14\6\uffff\1\16\11\uffff\1\13\22\uffff\1\15\11\uffff\1\16\10\uffff\1\23\12\uffff\1\16\5\uffff\1\16\5\uffff\1\16\5\uffff\1\12\13\uffff\1\4\1\5\1\uffff\1\6\1\7\1\10\4\uffff\1\11\1\3\1\2\1\1",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_14 = DFA.unpackEncodedString(dfa_14s);
    static final char[] dfa_15 = DFA.unpackEncodedStringToUnsignedChars(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final short[] dfa_17 = DFA.unpackEncodedString(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[][] dfa_19 = unpackEncodedStringArray(dfa_19s);

    class DFA134 extends DFA {

        public DFA134(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 134;
            this.eot = dfa_14;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "7360:1: ( ( (lv_wop_1_0= ruleOperandGroup ) ) | ( (lv_expr_2_0= ruleFullExpression ) ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA134_1 = input.LA(1);

                         
                        int index134_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred176_InternalSqlParser()) ) {s = 20;}

                        else if ( (synpred177_InternalSqlParser()) ) {s = 14;}

                         
                        input.seek(index134_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA134_2 = input.LA(1);

                         
                        int index134_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred176_InternalSqlParser()) ) {s = 20;}

                        else if ( (synpred177_InternalSqlParser()) ) {s = 14;}

                         
                        input.seek(index134_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA134_3 = input.LA(1);

                         
                        int index134_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred176_InternalSqlParser()) ) {s = 20;}

                        else if ( (synpred177_InternalSqlParser()) ) {s = 14;}

                         
                        input.seek(index134_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA134_4 = input.LA(1);

                         
                        int index134_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred176_InternalSqlParser()) ) {s = 20;}

                        else if ( (synpred177_InternalSqlParser()) ) {s = 14;}

                         
                        input.seek(index134_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA134_5 = input.LA(1);

                         
                        int index134_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred176_InternalSqlParser()) ) {s = 20;}

                        else if ( (synpred177_InternalSqlParser()) ) {s = 14;}

                         
                        input.seek(index134_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA134_6 = input.LA(1);

                         
                        int index134_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred176_InternalSqlParser()) ) {s = 20;}

                        else if ( (synpred177_InternalSqlParser()) ) {s = 14;}

                         
                        input.seek(index134_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA134_7 = input.LA(1);

                         
                        int index134_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred176_InternalSqlParser()) ) {s = 20;}

                        else if ( (synpred177_InternalSqlParser()) ) {s = 14;}

                         
                        input.seek(index134_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA134_8 = input.LA(1);

                         
                        int index134_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred176_InternalSqlParser()) ) {s = 20;}

                        else if ( (synpred177_InternalSqlParser()) ) {s = 14;}

                         
                        input.seek(index134_8);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA134_9 = input.LA(1);

                         
                        int index134_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred176_InternalSqlParser()) ) {s = 20;}

                        else if ( (synpred177_InternalSqlParser()) ) {s = 14;}

                         
                        input.seek(index134_9);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA134_10 = input.LA(1);

                         
                        int index134_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred176_InternalSqlParser()) ) {s = 20;}

                        else if ( (synpred177_InternalSqlParser()) ) {s = 14;}

                         
                        input.seek(index134_10);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA134_11 = input.LA(1);

                         
                        int index134_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred176_InternalSqlParser()) ) {s = 20;}

                        else if ( (synpred177_InternalSqlParser()) ) {s = 14;}

                         
                        input.seek(index134_11);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA134_12 = input.LA(1);

                         
                        int index134_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred176_InternalSqlParser()) ) {s = 20;}

                        else if ( (synpred177_InternalSqlParser()) ) {s = 14;}

                         
                        input.seek(index134_12);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA134_13 = input.LA(1);

                         
                        int index134_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred176_InternalSqlParser()) ) {s = 20;}

                        else if ( (synpred177_InternalSqlParser()) ) {s = 14;}

                         
                        input.seek(index134_13);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 134, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_20s = "\24\uffff";
    static final String dfa_21s = "\1\42\15\0\6\uffff";
    static final String dfa_22s = "\1\u008f\15\0\6\uffff";
    static final String dfa_23s = "\16\uffff\1\2\4\uffff\1\1";
    static final String dfa_24s = "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\6\uffff}>";
    static final String[] dfa_25s = {
            "\1\14\6\uffff\1\16\11\uffff\1\13\22\uffff\1\15\11\uffff\1\16\23\uffff\1\16\5\uffff\1\16\5\uffff\1\16\5\uffff\1\12\13\uffff\1\4\1\5\1\uffff\1\6\1\7\1\10\4\uffff\1\11\1\3\1\2\1\1",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_20 = DFA.unpackEncodedString(dfa_20s);
    static final char[] dfa_21 = DFA.unpackEncodedStringToUnsignedChars(dfa_21s);
    static final char[] dfa_22 = DFA.unpackEncodedStringToUnsignedChars(dfa_22s);
    static final short[] dfa_23 = DFA.unpackEncodedString(dfa_23s);
    static final short[] dfa_24 = DFA.unpackEncodedString(dfa_24s);
    static final short[][] dfa_25 = unpackEncodedStringArray(dfa_25s);

    class DFA137 extends DFA {

        public DFA137(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 137;
            this.eot = dfa_20;
            this.eof = dfa_20;
            this.min = dfa_21;
            this.max = dfa_22;
            this.accept = dfa_23;
            this.special = dfa_24;
            this.transition = dfa_25;
        }
        public String getDescription() {
            return "7506:1: ( ( (lv_wop_1_0= ruleOperandGroup ) ) | ( (lv_expr_2_0= ruleFullExpression ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA137_1 = input.LA(1);

                         
                        int index137_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalSqlParser()) ) {s = 19;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index137_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA137_2 = input.LA(1);

                         
                        int index137_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalSqlParser()) ) {s = 19;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index137_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA137_3 = input.LA(1);

                         
                        int index137_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalSqlParser()) ) {s = 19;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index137_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA137_4 = input.LA(1);

                         
                        int index137_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalSqlParser()) ) {s = 19;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index137_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA137_5 = input.LA(1);

                         
                        int index137_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalSqlParser()) ) {s = 19;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index137_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA137_6 = input.LA(1);

                         
                        int index137_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalSqlParser()) ) {s = 19;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index137_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA137_7 = input.LA(1);

                         
                        int index137_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalSqlParser()) ) {s = 19;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index137_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA137_8 = input.LA(1);

                         
                        int index137_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalSqlParser()) ) {s = 19;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index137_8);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA137_9 = input.LA(1);

                         
                        int index137_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalSqlParser()) ) {s = 19;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index137_9);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA137_10 = input.LA(1);

                         
                        int index137_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalSqlParser()) ) {s = 19;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index137_10);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA137_11 = input.LA(1);

                         
                        int index137_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalSqlParser()) ) {s = 19;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index137_11);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA137_12 = input.LA(1);

                         
                        int index137_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalSqlParser()) ) {s = 19;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index137_12);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA137_13 = input.LA(1);

                         
                        int index137_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalSqlParser()) ) {s = 19;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index137_13);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 137, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000400000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x000000000000E000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000000L,0x0040400000000000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000002L,0x0200000000000000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000000L,0x0000002000100000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0400010000040002L,0x0000000000000004L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000400000000000L,0x0000000024000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0008000408000000L,0x0040004000000040L,0x000000000000F0FCL});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x00000000000000C0L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0008002408000000L,0x0040004004000040L,0x000000000000F0FCL});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0008000408000000L,0x0040004004000040L,0x000000000000F0FCL});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x000000000000E000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x22A0140000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0008020400000000L,0x0041041000010040L,0x000000000000F0ECL});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x22A0140000000002L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x000000000000E020L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x2220140000000002L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x2220100000000002L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0220100000000002L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0020100000000002L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0020000000000002L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000000002L,0x0000400000000000L,0x000000000000E000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x000000000000E000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000000002L,0x0800000000000000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0110001000000202L,0x0000000000005402L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000008L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x8000008000000002L,0x0000400000000000L,0x000000000000E000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L,0x00000000000010ECL});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000000L,0x0040008000000000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000000L,0x0040000080000000L,0x000000000000E000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000A00000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000002L,0x0000400000000000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000002L,0x0000000100000080L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000000000002L,0x0008000040000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0007000063C10000L,0x0000010000000020L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0008000400000000L,0x0040000000000040L,0x000000000000F0ECL});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0008020480000000L,0xE0533E1000018040L,0x000000000000F0ECL});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000000L,0x0000001000020000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0008000400000000L,0x00400000A0200040L,0x000000000000F0ECL});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0008000000000000L,0x0000000000000000L,0x00000000000090ECL});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x00000000000017ECL});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000000000002L,0x1520000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0008000408000000L,0x00C0000020000040L,0x000000000000F0FCL});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000000000002L,0x0000000000080000L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x080028400400FDF0L,0x0000000209000800L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x2000000000080000L,0x0080000000000000L});
    public static final BitSet FOLLOW_80 = new BitSet(new long[]{0x2000000000000002L});
    public static final BitSet FOLLOW_81 = new BitSet(new long[]{0x0000000000000002L,0x0000000000100001L});
    public static final BitSet FOLLOW_82 = new BitSet(new long[]{0x0008000580200000L,0x0040000000000040L,0x000000000000F0ECL});
    public static final BitSet FOLLOW_83 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_84 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_85 = new BitSet(new long[]{0x0000000000120000L});
    public static final BitSet FOLLOW_86 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_87 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_88 = new BitSet(new long[]{0x1000000000000002L,0x0000000100000080L});
    public static final BitSet FOLLOW_89 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_90 = new BitSet(new long[]{0x0040000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_91 = new BitSet(new long[]{0x0008000408000000L,0x0040000020000040L,0x000000000000F0ECL});
    public static final BitSet FOLLOW_92 = new BitSet(new long[]{0x0000000000000000L,0x00C0000000000000L});
    public static final BitSet FOLLOW_93 = new BitSet(new long[]{0x0000000000000000L,0x0280000000000000L});
    public static final BitSet FOLLOW_94 = new BitSet(new long[]{0x0000000000000002L,0x0000000010000000L});
    public static final BitSet FOLLOW_95 = new BitSet(new long[]{0x0008020400000000L,0x0041041002010040L,0x000000000000F0ECL});
    public static final BitSet FOLLOW_96 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_97 = new BitSet(new long[]{0x0008020400000002L,0x0041041002010040L,0x000000000000F0ECL});
    public static final BitSet FOLLOW_98 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_99 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000100L});
    public static final BitSet FOLLOW_100 = new BitSet(new long[]{0x0110000000000200L,0x0000000000005402L});
    public static final BitSet FOLLOW_101 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_102 = new BitSet(new long[]{0x4000000000000000L,0x0000000000001000L});

}