/*
 * $Id: Test.java,v 1.1 2006/04/15 14:40:06 platform Exp $
 * Created on 2006-4-15
 */
package org.json.simple;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

public class TestJson {

    @Test
    public void testDecode() throws Exception {
        System.out.println("=======decode=======");

        String s = "[0,10]";
        Object obj = JSONValue.parse(s);
        JSONArray array = (JSONArray) obj;
        System.out.println("======the 2nd element of array======");
        System.out.println(array.get(1));
        System.out.println();
        assertEquals("10", array.get(1).toString());
    }

    @Test
    public void testJSONArrayCollection() {
        final ArrayList<String> testList = new ArrayList<String>();
        testList.add("First item");
        testList.add("Second item");
        final JSONArray jsonArray = new JSONArray(testList);

        assertEquals("[\"First item\",\"Second item\"]", jsonArray.toJSONString());
    }

    @Test
    public void testJsonObject() {
        JSONObject studentOne = new JSONObject();
        studentOne.put("name", "ali");
        studentOne.put("grade", 10);
        JSONObject studentTwo = new JSONObject();
        studentTwo.put("name", "mohammad");
        studentTwo.put("grade", 20);
        JSONObject studentThree = new JSONObject();
        studentThree.put("name", "hasan");
        studentThree.put("grade", 15);
        JSONArray students_in_order = new JSONArray();
        students_in_order.add(studentOne);
        students_in_order.add(studentThree);
        students_in_order.add(studentTwo);
        int[] grades = new int[students_in_order.size()];
        int sum = 0, index = 0;
        for (Object jsonObject : students_in_order) {
            JSONObject student = (JSONObject) jsonObject;
            sum += (int) student.get("grade");
            grades[index] = (int) student.get("grade");
            index++;
        }
        // grades
        assertEquals("[10,15,20]", JSONArray.toJSONString(grades));
        int average = sum / students_in_order.size();
        assertEquals(15, average);
        JSONObject top_student = (JSONObject) students_in_order.get(students_in_order.size() - 1);
        assertEquals("{\"grade\":20,\"name\":\"mohammad\"}", top_student.toString());
        JSONObject lazy_student = (JSONObject) students_in_order.get(0);
        assertEquals("\"lazy student\":{\"grade\":10,\"name\":\"ali\"}", JSONObject.toString("lazy student", lazy_student));
    }

    @Test
    public void testParserException() throws Exception {
        System.out.println("=======decode=======");

        String s = "[0,10)";
        try {
            Object obj = JSONValue.parseWithException(s);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            if (e.getErrorType() == ParseException.ERROR_UNEXPECTED_CHAR) {
                System.out.println("autocorrect input string");
                s = s.substring(0, e.getPosition()) + "]" + s.substring(e.getPosition() + 1);
                Object obj = JSONValue.parseWithException(s);
                JSONArray array = (JSONArray) obj;
                System.out.println("======the 2nd element of array======");
                System.out.println(array.get(1));
                System.out.println();
                assertEquals("10", array.get(1).toString());
            }
        }
    }

    @Test
    public void testNestedDecode() throws ParseException {
        String flight = "{\"location\":{\"from\":\"Tehran\",\"to\":\"Paris\"},\"time\":{\"boarding\":\"8:30\",\"take_off\":\"9:30\"},\"passengers\":[\"ali\",\"hossein\"]}\n";
        JSONObject obj = (JSONObject) JSONValue.parseWithException(flight);
        JSONArray passengers = (JSONArray) obj.get("passengers");
        System.out.println("passengers:");
        System.out.println(passengers.toString());
        JSONObject location = (JSONObject) obj.get("location");
        assertEquals("Tehran", location.get("from"));
        assertEquals("Paris", location.get("to"));

    }
}
