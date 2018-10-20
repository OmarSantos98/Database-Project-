import engine.exception.DatabaseCoreException;
import gen.DMLBaseListener;
import gen.DMLListener;
import gen.DMLParser;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import parser.DMLErrorListener;
import parser.DMLReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String args[]) throws IOException {
        // Gets a parser one line at a time from the input stream, terminate on EOF.
        DMLReader reader = new DMLReader();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        DMLListener listener = new DMLBaseListener();
        for (; ; line = buffer.readLine()) {
            try {
                if (line == null) continue;
                DMLParser parser = reader.GetParser(line);
                DMLParser.ProgramContext program = parser.program();
                ParseTreeWalker walker = new ParseTreeWalker();
                if (DMLErrorListener.HASERROR) continue;
                walker.walk(listener, program);
            } catch (DatabaseCoreException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {

                System.out.println(e.getMessage());
            }
        }

        /*Relation employee = new Relation("employee");
        String columns[] = {"id:INTEGER:1",
                "name:VARCHAR5:0",
                "salary:INTEGER:0"};
        employee.SetColumns(columns);
        employee.AddRow(1, "joe", 1500);
        employee.AddRow(2, "sam", 2400);
        employee.AddRow(3, "sally", 3000);
        employee.AddRow(4, "mat", 1200);

        DatabaseCore dbCore = new DatabaseCore();
        System.out.println("Original relation: ");
        dbCore.Show(employee);
        System.out.println();

        System.out.println("(1) Selecting tuples where `salary > 2000`: ");
        Relation result01 = dbCore.Select(employee, new GreaterThan("salary", 2000));
        dbCore.Show(result01);
        System.out.println();

        //Selecting
        System.out.println("(2) Selecting tuples where `salary == 1500`: ");
        Relation result02 = dbCore.Select(employee, new Equals("salary", 1500));
        dbCore.Show(result02);
        System.out.println();

        //Union
        System.out.println("(3) Union of the previous two result relations: ");
        Relation result03 = dbCore.Union(result01, result02);
        if (result03 != null)
            dbCore.Show(result03);
        System.out.println();

        //Difference
        System.out.println("(4) Difference between the original relation and (2): ");
        Relation result04 = dbCore.Difference(result02, employee);
        if (result04 != null)
            dbCore.Show(result04);
        System.out.println();

        System.out.println("(5) Renaming `id` attribute to `employeeId`: ");
        Relation result05 = dbCore.Rename(employee, Arrays.asList("employeeId", "name", "salary"));
        dbCore.Show(result05);
        System.out.println();

        //Natural Join
        Relation contact = new Relation("employee_contact");
        String columns2[] = {"id:INTEGER:1",
                "address:VARCHAR20:0"};
        contact.SetColumns(columns2);
        contact.AddRow(1, "1223 CSTAT");
        contact.AddRow(1, "2490, NY");
        contact.AddRow(3, "1241 WA");

        System.out.println("(6) Natural join of employee and contact relations over employeeId: ");
        Relation result06 = dbCore.NaturalJoin(employee, contact);
        dbCore.Show(result06);
        System.out.println();

        //Projection testing
        System.out.println("(7) Projection of employee names");
        List<String> attriList = new ArrayList<>();
        attriList.add("id");
        attriList.add("name");


        Relation result07 = dbCore.Project(employee, attriList);
        dbCore.Show(result07);
        System.out.println();

        System.out.println("(8) Product of contact and employee");
        Relation result08 = dbCore.Product(employee, contact);
        dbCore.Show(result08);
        System.out.println();

        // Create
        System.out.println("(9) Create employeeLocation:");
        Relation result09 = dbCore.Create("employeeLocation", Arrays.asList(
                new Column("employeeId", ColumnType.INTEGER, true),
                new Column("location", ColumnType.VARCHAR, false, 10)
        ));
        dbCore.Show(result09);
        System.out.println();

        // Insert (literals)
        System.out.println("(10) Insert (literals) into employeeLocation:");

        dbCore.Insert(result09, Arrays.asList(1, "RDMC 110"));
        dbCore.Insert(result09, Arrays.asList(4, "HRBB 113"));
        dbCore.Show(result09);
        System.out.println();

        // Insert (from relation)
        System.out.println("(11) Insert (from relation) into employeeLocation:");
        Relation toInsert = dbCore.Create("toInsert", Arrays.asList(
                new Column("employeeId", ColumnType.INTEGER, true),
                new Column("location", ColumnType.VARCHAR, false, 10)
        ));

        dbCore.Insert(toInsert, Arrays.asList(2, "MSC 100"));
        dbCore.Insert(toInsert, Arrays.asList(3, "BLOC 777"));

        dbCore.Insert(result09, toInsert);
        dbCore.Show(result09);
        System.out.println();

        // Update
        System.out.println("(12) Update employeeLocation's locations to Chicken");

        dbCore.Update(result09, new LessThanEqual("employeeId", 20), Arrays.asList(
                new Pair<>("location", "Westeros")
        ));
        dbCore.Show(result09);
        System.out.println();

        //Delete
        System.out.println("(13) Delete row if salary == 1500");
        dbCore.Delete(employee, new Equals("salary", 1500));
        dbCore.Show(employee);

        System.out.println();

        // Open
        System.out.println("(14) Open");
        Relation test = dbCore.Open("test");
        dbCore.Show(test);
        System.out.println();

        // Write
        System.out.println("(15) Write (then read back in and show)");
        dbCore.Write(employee);
        Relation open = dbCore.Open("employee");
        dbCore.Show(open);
        System.out.println();

        // Close
        System.out.println("(15) Close");
        Relation close = dbCore.Open("test");
        close.AddRow("40", 40);
        dbCore.Close(close);
        Relation fin = dbCore.Open("test");
        dbCore.Show(fin);
        System.out.println();*/

    }
}
