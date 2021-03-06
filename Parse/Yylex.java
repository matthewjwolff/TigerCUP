package Parse;
import ErrorMsg.ErrorMsg;


class Yylex implements Lexer {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final char YYEOF = '\uFFFF';

private void newline() {
  errorMsg.newline(yychar);
}
private void err(int pos, String s) {
  errorMsg.error(pos,s);
}
private void err(String s) {
  err(yychar,s);
}
private java_cup.runtime.Symbol tok(int kind) {
    return tok(kind, null);
}
private java_cup.runtime.Symbol tok(int kind, Object value) {
    return new java_cup.runtime.Symbol(kind, yychar, yychar+yylength(), value);
}
private ErrorMsg errorMsg;
Yylex(java.io.InputStream s, ErrorMsg e) {
  this(s);
  errorMsg=e;
}
private int commentDepth = 0;
private StringBuffer buffer;
private int stringl;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yy_lexical_state;

	Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int STRING = 2;
	private final int YYINITIAL = 0;
	private final int COMMENT = 1;
	private final int IGNORE = 3;
	private final int yy_state_dtrans[] = {
		0,
		58,
		62,
		71
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private char yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YYEOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YYEOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_start () {
		++yychar;
		++yy_buffer_start;
	}
	private void yy_pushback () {
		--yy_buffer_end;
	}
	private void yy_mark_start () {
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
private int [][] unpackFromString(int size1, int size2, String st)
    {
      int colonIndex = -1;
      String lengthString;
      int sequenceLength = 0;
      int sequenceInteger = 0;
      int commaIndex;
      String workString;
      int res[][] = new int[size1][size2];
      for (int i= 0; i < size1; i++)
	for (int j= 0; j < size2; j++)
	  {
	    if (sequenceLength == 0) 
	      {	
		commaIndex = st.indexOf(',');
		if (commaIndex == -1)
		  workString = st;
		else
		  workString = st.substring(0, commaIndex);
		st = st.substring(commaIndex+1);
		colonIndex = workString.indexOf(':');
		if (colonIndex == -1)
		  {
		    res[i][j] = Integer.parseInt(workString);
		  }
		else 
		  {
		    lengthString = workString.substring(colonIndex+1);  
		    sequenceLength = Integer.parseInt(lengthString);
		    workString = workString.substring(0,colonIndex);
		    sequenceInteger = Integer.parseInt(workString);
		    res[i][j] = sequenceInteger;
		    sequenceLength--;
		  }
	      }
	    else 
	      {
		res[i][j] = sequenceInteger;
		sequenceLength--;
	      }
	  }
      return res;
    }
	private int yy_acpt[] = {
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR
	};
	private int yy_cmap[] = {
		0, 0, 0, 0, 0, 0, 0, 0,
		1, 1, 2, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 3,
		1, 0, 4, 0, 0, 0, 5, 0,
		6, 7, 8, 9, 10, 11, 12, 13,
		14, 14, 14, 14, 14, 14, 14, 14,
		14, 14, 15, 16, 17, 18, 19, 0,
		3, 20, 20, 20, 20, 20, 20, 20,
		20, 20, 20, 20, 20, 20, 20, 20,
		20, 20, 20, 20, 20, 20, 20, 20,
		20, 20, 20, 21, 22, 23, 24, 25,
		0, 26, 27, 28, 29, 30, 31, 20,
		32, 33, 20, 34, 35, 20, 36, 37,
		38, 20, 39, 40, 41, 42, 43, 44,
		20, 45, 20, 46, 47, 48, 0, 0
		
	};
	private int yy_rmap[] = {
		0, 1, 1, 1, 1, 1, 1, 1,
		1, 1, 1, 1, 2, 3, 4, 1,
		5, 1, 6, 7, 1, 1, 1, 1,
		1, 1, 1, 1, 1, 1, 7, 7,
		7, 7, 7, 7, 7, 7, 7, 7,
		7, 7, 7, 7, 7, 7, 7, 1,
		1, 1, 1, 1, 1, 1, 1, 1,
		1, 1, 8, 9, 10, 11, 12, 13,
		14, 15, 16, 17, 18, 19, 20, 21,
		22, 23, 24, 25, 26, 27, 28, 29,
		30, 31, 32, 33, 34, 35, 36, 37,
		38, 39, 40, 41, 42, 43, 44, 45,
		46, 47, 48, 49, 50, 51, 52, 53,
		54 
	};
	private int yy_nxt[][] = unpackFromString(55,49,
"1,2:2,1,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,21,1:2,99,100,19,60,83,84,19,63,19,85,86,66,19:3,68,19,87,101,19,22,23,24,-1:57,25,-1:54,13,-1:52,26,-1:48,27,28,-1:47,29,-1:44,19,-1:5,19,-1:4,19:21,-1:3,47:2,48,47:5,61,47:4,64,47:35,-1,53:2,-1,54,-1:9,65,-1:7,54,-1,67,-1:11,54,-1:4,54,-1:21,19,-1:5,19,-1:4,19:12,30,19:8,-1:16,49,-1:35,51:4,52,51:17,59,51:26,-1:14,19,-1:5,19,-1:4,19:6,31,19:4,32,19:9,-1:11,50,-1:54,69,-1:48,19,-1:5,19,-1:4,19:6,33,19:14,-1:6,54,-1:16,54:4,-1:2,54:20,-1:17,19,-1:5,19,-1:4,19:7,89,19:4,34,19:7,90,-1:17,54,-1:48,19,-1:5,19,-1:4,19:4,35,19:16,-1:3,55,56:2,55:19,57,55:26,-1:14,19,-1:5,19,-1:4,19:14,36,19:6,-1:17,19,-1:5,19,-1:4,19:16,37,19:4,-1:17,19,-1:5,19,-1:4,19:10,38,19:10,-1:17,19,-1:5,19,-1:4,19:14,39,19:6,-1:17,19,-1:5,19,-1:4,19:5,40,19:15,-1:17,19,-1:5,19,-1:4,19:11,41,19:9,-1:17,19,-1:5,19,-1:4,19:5,42,19:15,-1:17,19,-1:5,19,-1:4,19:20,43,-1:17,19,-1:5,19,-1:4,19:9,44,19:11,-1:17,19,-1:5,19,-1:4,19:5,45,19:15,-1:17,19,-1:5,19,-1:4,19:11,46,19:9,-1:17,19,-1:5,19,-1:4,19:10,88,70,19:9,-1:17,19,-1:5,19,-1:4,19:12,72,19:4,104,19:3,-1:17,19,-1:5,19,-1:4,19:5,73,19:15,-1:17,19,-1:5,19,-1:4,19:8,74,19:12,-1:17,19,-1:5,19,-1:4,19,75,19:19,-1:17,19,-1:5,19,-1:4,19:15,76,19:5,-1:17,19,-1:5,19,-1:4,19:5,77,19:15,-1:17,19,-1:5,19,-1:4,19:13,78,19:7,-1:17,19,-1:5,19,-1:4,19,79,19:19,-1:17,19,-1:5,19,-1:4,19,80,19:19,-1:17,19,-1:5,19,-1:4,19:10,81,19:10,-1:17,19,-1:5,19,-1:4,19:12,82,19:8,-1:17,19,-1:5,19,-1:4,19:14,91,19:6,-1:17,19,-1:5,19,-1:4,19:5,92,19:15,-1:17,19,-1:5,19,-1:4,19:8,93,19:12,-1:17,19,-1:5,19,-1:4,19:8,94,19:12,-1:17,19,-1:5,19,-1:4,19:14,95,19:6,-1:17,19,-1:5,19,-1:4,19:14,96,19:6,-1:17,19,-1:5,19,-1:4,19:7,97,19:13,-1:17,19,-1:5,19,-1:4,19:16,98,19:4,-1:17,19,-1:5,19,-1:4,19:3,102,19:17,-1:17,19,-1:5,19,-1:4,19:11,103,19:9,-1:3");
	public java_cup.runtime.Symbol nextToken ()
		throws java.io.IOException {
		char yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			if (YYEOF != yy_lookahead) {
				yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YYEOF == yy_lookahead && true == yy_initial) {

	{
    //state is stored in variable yy_lexical_state, states are declared as final int STATENAME
    if(yy_lexical_state==COMMENT)
      err("Reached end of file with unterminated comment, proceeding anyway.");
    if(yy_lexical_state==STRING) {
      err("Reached end of file with unterminated string, giving the token we got so far");
      yybegin(YYINITIAL);
      return tok(sym.STRING, buffer.toString());
    }
	  return tok(sym.EOF, null);
  }
				}
				else if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_to_mark();
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_pushback();
					}
					if (0 != (YY_START & yy_anchor)) {
						yy_move_start();
					}
					switch (yy_last_accept_state) {
					case 1:
						{
  err("Illegal character: " + yytext() + "(code: " + (int)(yytext().charAt(0)) + ")");
}
					case -2:
						break;
					case 2:
						{}
					case -3:
						break;
					case 3:
						{
  //Found open quote, begin parsing string
  yybegin(STRING);
  buffer = new StringBuffer();
  stringl = yychar;
}
					case -4:
						break;
					case 4:
						{return tok(sym.AND, null);}
					case -5:
						break;
					case 5:
						{return tok(sym.LPAREN, null);}
					case -6:
						break;
					case 6:
						{return tok(sym.RPAREN, null);}
					case -7:
						break;
					case 7:
						{return tok(sym.TIMES, null);}
					case -8:
						break;
					case 8:
						{return tok(sym.PLUS, null);}
					case -9:
						break;
					case 9:
						{return tok(sym.COMMA, null);}
					case -10:
						break;
					case 10:
						{return tok(sym.MINUS, null);}
					case -11:
						break;
					case 11:
						{return tok(sym.DOT, null);}
					case -12:
						break;
					case 12:
						{return tok(sym.DIVIDE, null);}
					case -13:
						break;
					case 13:
						{
  return tok(sym.INT, Integer.parseInt(yytext()));
}
					case -14:
						break;
					case 14:
						{return tok(sym.COLON, null);}
					case -15:
						break;
					case 15:
						{return tok(sym.SEMICOLON, null);}
					case -16:
						break;
					case 16:
						{return tok(sym.LT, null);}
					case -17:
						break;
					case 17:
						{return tok(sym.EQ, null);}
					case -18:
						break;
					case 18:
						{return tok(sym.GT, null);}
					case -19:
						break;
					case 19:
						{
  return tok(sym.ID, yytext());
}
					case -20:
						break;
					case 20:
						{return tok(sym.LBRACK, null);}
					case -21:
						break;
					case 21:
						{return tok(sym.RBRACK, null);}
					case -22:
						break;
					case 22:
						{return tok(sym.LBRACE, null);}
					case -23:
						break;
					case 23:
						{return tok(sym.OR, null);}
					case -24:
						break;
					case 24:
						{return tok(sym.RBRACE, null);}
					case -25:
						break;
					case 25:
						{
  commentDepth++;
  yybegin(COMMENT);
}
					case -26:
						break;
					case 26:
						{return tok(sym.ASSIGN, null);}
					case -27:
						break;
					case 27:
						{return tok(sym.LE, null);}
					case -28:
						break;
					case 28:
						{return tok(sym.NEQ, null);}
					case -29:
						break;
					case 29:
						{return tok(sym.GE, null);}
					case -30:
						break;
					case 30:
						{return tok(sym.DO, null);}
					case -31:
						break;
					case 31:
						{return tok(sym.IF, null);}
					case -32:
						break;
					case 32:
						{return tok(sym.IN, null);}
					case -33:
						break;
					case 33:
						{return tok(sym.OF, null);}
					case -34:
						break;
					case 34:
						{return tok(sym.TO, null);}
					case -35:
						break;
					case 35:
						{return tok(sym.END, null);}
					case -36:
						break;
					case 36:
						{return tok(sym.FOR, null);}
					case -37:
						break;
					case 37:
						{return tok(sym.LET, null);}
					case -38:
						break;
					case 38:
						{return tok(sym.NIL, null);}
					case -39:
						break;
					case 39:
						{return tok(sym.VAR, null);}
					case -40:
						break;
					case 40:
						{return tok(sym.ELSE, null);}
					case -41:
						break;
					case 41:
						{return tok(sym.THEN, null);}
					case -42:
						break;
					case 42:
						{return tok(sym.TYPE, null);}
					case -43:
						break;
					case 43:
						{return tok(sym.ARRAY, null);}
					case -44:
						break;
					case 44:
						{return tok(sym.BREAK, null);}
					case -45:
						break;
					case 45:
						{return tok(sym.WHILE, null);}
					case -46:
						break;
					case 46:
						{return tok(sym.FUNCTION, null);}
					case -47:
						break;
					case 47:
						{}
					case -48:
						break;
					case 48:
						{}
					case -49:
						break;
					case 49:
						{
  commentDepth--;
  if(commentDepth==0)
    yybegin(YYINITIAL);
  if(commentDepth<0)
    err("Uhh we're in negative comments now, something went really wrong.");
}
					case -50:
						break;
					case 50:
						{commentDepth++;}
					case -51:
						break;
					case 51:
						{
  //All text that is not \ or ". Parse for escape for \ and finish string at ".
  buffer.append(yytext());
}
					case -52:
						break;
					case 52:
						{
  //Found ending quote
  yybegin(YYINITIAL);
  String val = buffer.toString();
  //Since we're building the string characterwise, we have to reconstruct the character positions...
  return new java_cup.runtime.Symbol(sym.STRING, stringl, yychar, val);
}
					case -53:
						break;
					case 53:
						{
  yybegin(IGNORE);
}
					case -54:
						break;
					case 54:
						{
  //Backslash followed by one of n,t,\,",###,or a ^CONTROL
  String escape = yytext().substring(1,yytext().length());
  if(escape.charAt(0)=='n')
    buffer.append("\n");
  else if(escape.charAt(0)=='t')
    buffer.append("\t");
  else if(escape.charAt(0)=='^') {
    //control nonsense
    char controlCode = escape.charAt(1);
    char control;
    if(controlCode>96)
      control = (char)(controlCode-96);
    else control = (char)(controlCode-64);
    buffer.append(control);
  }
  else if(escape.charAt(0)=='\\')
    buffer.append("\\");
  else if(escape.charAt(0)=='\"')
    buffer.append("\"");
  else {
    try{
      int uni = Integer.parseInt(escape.substring(1,escape.length()));
      char uniChar = (char)uni;
      buffer.append(uniChar);
    } catch(NumberFormatException e) {
    }
  }
}
					case -55:
						break;
					case 55:
						{
  err("Illegal non-whitespace character: "+yytext()+"(code: "+(int)(yytext().charAt(0))+")");
}
					case -56:
						break;
					case 56:
						{}
					case -57:
						break;
					case 57:
						{
  yybegin(STRING);
}
					case -58:
						break;
					case 59:
						{
  err("Illegal character: " + yytext() + "(code: " + (int)(yytext().charAt(0)) + ")");
}
					case -59:
						break;
					case 60:
						{
  return tok(sym.ID, yytext());
}
					case -60:
						break;
					case 61:
						{}
					case -61:
						break;
					case 63:
						{
  return tok(sym.ID, yytext());
}
					case -62:
						break;
					case 64:
						{}
					case -63:
						break;
					case 66:
						{
  return tok(sym.ID, yytext());
}
					case -64:
						break;
					case 68:
						{
  return tok(sym.ID, yytext());
}
					case -65:
						break;
					case 70:
						{
  return tok(sym.ID, yytext());
}
					case -66:
						break;
					case 72:
						{
  return tok(sym.ID, yytext());
}
					case -67:
						break;
					case 73:
						{
  return tok(sym.ID, yytext());
}
					case -68:
						break;
					case 74:
						{
  return tok(sym.ID, yytext());
}
					case -69:
						break;
					case 75:
						{
  return tok(sym.ID, yytext());
}
					case -70:
						break;
					case 76:
						{
  return tok(sym.ID, yytext());
}
					case -71:
						break;
					case 77:
						{
  return tok(sym.ID, yytext());
}
					case -72:
						break;
					case 78:
						{
  return tok(sym.ID, yytext());
}
					case -73:
						break;
					case 79:
						{
  return tok(sym.ID, yytext());
}
					case -74:
						break;
					case 80:
						{
  return tok(sym.ID, yytext());
}
					case -75:
						break;
					case 81:
						{
  return tok(sym.ID, yytext());
}
					case -76:
						break;
					case 82:
						{
  return tok(sym.ID, yytext());
}
					case -77:
						break;
					case 83:
						{
  return tok(sym.ID, yytext());
}
					case -78:
						break;
					case 84:
						{
  return tok(sym.ID, yytext());
}
					case -79:
						break;
					case 85:
						{
  return tok(sym.ID, yytext());
}
					case -80:
						break;
					case 86:
						{
  return tok(sym.ID, yytext());
}
					case -81:
						break;
					case 87:
						{
  return tok(sym.ID, yytext());
}
					case -82:
						break;
					case 88:
						{
  return tok(sym.ID, yytext());
}
					case -83:
						break;
					case 89:
						{
  return tok(sym.ID, yytext());
}
					case -84:
						break;
					case 90:
						{
  return tok(sym.ID, yytext());
}
					case -85:
						break;
					case 91:
						{
  return tok(sym.ID, yytext());
}
					case -86:
						break;
					case 92:
						{
  return tok(sym.ID, yytext());
}
					case -87:
						break;
					case 93:
						{
  return tok(sym.ID, yytext());
}
					case -88:
						break;
					case 94:
						{
  return tok(sym.ID, yytext());
}
					case -89:
						break;
					case 95:
						{
  return tok(sym.ID, yytext());
}
					case -90:
						break;
					case 96:
						{
  return tok(sym.ID, yytext());
}
					case -91:
						break;
					case 97:
						{
  return tok(sym.ID, yytext());
}
					case -92:
						break;
					case 98:
						{
  return tok(sym.ID, yytext());
}
					case -93:
						break;
					case 99:
						{
  return tok(sym.ID, yytext());
}
					case -94:
						break;
					case 100:
						{
  return tok(sym.ID, yytext());
}
					case -95:
						break;
					case 101:
						{
  return tok(sym.ID, yytext());
}
					case -96:
						break;
					case 102:
						{
  return tok(sym.ID, yytext());
}
					case -97:
						break;
					case 103:
						{
  return tok(sym.ID, yytext());
}
					case -98:
						break;
					case 104:
						{
  return tok(sym.ID, yytext());
}
					case -99:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
					}
				}
			}
		}
	}
}
