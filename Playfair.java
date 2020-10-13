public class Playfair
{
    public static void main(String[] args)
    {
        String mode = args[0];
        String ciphertext = args[1];
        String keytext = args[2];

	//Global Step 0 - Formatting
	ciphertext = ciphertext.toUpperCase();
        ciphertext = ciphertext.replaceAll("\\s+","");


	//Routing
	String output = "";
	
	if (mode.equals("encode"))
	    output = Encode(ciphertext,keytext);

	else if (mode.equals("decode"))
	    output = Decode(ciphertext,keytext);
	
	else
	    output = "Unknown mode";

	System.out.println(output);

    
    }

    //Encode -------------------
    public static String Encode(String ciphertext, String keytext)
    {

	//Step 0.5: Turning Js into Is
	ciphertext = nomoreJs(ciphertext);


	//Step 1: Inserting x's to break apart double characters + adding one if odd

        ciphertext = xify(ciphertext);
	if (ciphertext.length() % 2 == 1)
	    ciphertext = ciphertext + "X";

	System.out.println(ciphertext);

	
	//Step 1.66: Breaking into pairs
	String[] pairs = pairify(ciphertext);


	//Step 2: Encoding

	for (int i = 0; i < pairs.length; i++)
	    encode(i,pairs,keytext);

	//Step 3: Output
	String output = "";
	for (String x: pairs)
	    output += x;

	return output;
    }
    
    public static String nomoreJs(String input) // Step 0.5 
    {
	int index = 0;

	while(input.length() > index+1)
	    {
		if (input.charAt(index) == 'J')
		    input = input.substring(0,index) + "I" + input.substring(index+1);
		index++;
		
	    }

	return input;
    }

    
    public static String xify(String input) // Step 1 
    {
        int index = 0;
        
        while(input.length() > index+1)
        {
            if (input.charAt(index) == input.charAt(index+1))
            
                input = input.substring(0,index+1) + "X" + input.substring(index+1);
            
            index += 2;
        }
        return input;
    }



    public static String[] pairify(String input) // Step 1.66
    {
        String newinput = "";
	
	for (int i =0; i < input.length(); i+=2)
	    newinput += input.substring(i,i+2) + " ";
	
	String[] output = newinput.split("\\s+");

	return output;
    }


    //Step 2:
    public static void encode(int index, String[] pairs, String keytext)
    {
	//pair stuff
	String pair = pairs[index];
	String first = "" + pair.charAt(0);
	String second = "" + pair.charAt(1);

	int column1 = keytext.indexOf(first) % 5; //first is 0, last is 4
	int row1 = keytext.indexOf(first) / 5;

	int column2 = keytext.indexOf(second) % 5;
	int row2 = keytext.indexOf(second) / 5;
	
	//Vertical
	if (row1 == row2)
	    pairs[index] = verticalEncode(row1, column1, column2, keytext);


	//Horizontal
	else if (column1 == column2)
	    pairs[index] = horizontalEncode(column1, row1, row2, keytext);

	//Regular

	else
	    pairs[index] = regularEncode(row1, row2, column1, column2, keytext);
    }

    public static String verticalEncode(int row1, int column1, int column2, String keytext)
    {
	if ((row1+1)*5 >= 25)
	    row1 = -1;
	String output = keytext.charAt((row1+1)*5 + column1) + "" + keytext.charAt((row1+1)*5 + column2);
	return output;
    }

    public static String horizontalEncode(int column1, int row1, int row2, String keytext)
    {
	if ((column1+1) >= 5)
	    column1 = -1;
	String output = keytext.charAt((row1*5 + column1+1)) + "" + keytext.charAt(row2*5 + column1+1);
	return output;
    }

    public static String regularEncode(int row1, int row2, int column1, int column2, String keytext)
    {
	String output = keytext.charAt(row1*5 + column2) + "" + keytext.charAt(row2*5 + column1);
	return output;
    }


    //Decode ----------- Not As Many Steps, Just Tracing Backwards

    public static String Decode(String ciphertext, String keytext)
    {
	//Step 1: Breaking into pairs
	String[] pairs = pairify(ciphertext);

	//Step 2: Decode (we got here fast this time)
	
	for (int i = 0; i < pairs.length; i++)
	    decode(i,pairs,keytext);

	//Step 3: Output
	String output = "";
	for (String x: pairs)
	    output += x;

	return output;
    }

    // Step 2 For Decode
        public static void decode(int index, String[] pairs, String keytext)
    {
	//pair stuff
	String pair = pairs[index];
	String first = "" + pair.charAt(0);
	String second = "" + pair.charAt(1);

	int column1 = keytext.indexOf(first) % 5; //first is 0, last is 4
	int row1 = keytext.indexOf(first) / 5;

	int column2 = keytext.indexOf(second) % 5;
	int row2 = keytext.indexOf(second) / 5;
	
	//Vertical
	if (row1 == row2)
	    pairs[index] = verticalDecode(row1, column1, column2, keytext);


	//Horizontal
	else if (column1 == column2)
	    pairs[index] = horizontalDecode(column1, row1, row2, keytext);

	//Regular

	else
	    pairs[index] = regularDecode(row1, row2, column1, column2, keytext);
    }

    public static String verticalDecode(int row1, int column1, int column2, String keytext)
    {
	if ((row1-1)*5 < 0)
	    row1 = 5;
	String output = keytext.charAt((row1-1)*5 + column1) + "" + keytext.charAt((row1-1)*5 + column2);
	return output;
    }

    public static String horizontalDecode(int column1, int row1, int row2, String keytext)
    {
	if ((column1-1) < 0)
	    column1 = 5;
	String output = keytext.charAt((row1*5 + column1-1)) + "" + keytext.charAt(row2*5 + column1-1);
	return output;
    }

    public static String regularDecode(int row1, int row2, int column1, int column2, String keytext)
    {
	String output = keytext.charAt(row1*5 + column2) + "" + keytext.charAt(row2*5 + column1);
	return output;
    }
    
}
