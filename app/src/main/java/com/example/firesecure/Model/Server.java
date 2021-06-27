package com.example.firesecure.Model;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firesecure.R;
import com.example.firesecure.View_and_Presenter.AddDivision;
import com.example.firesecure.View_and_Presenter.AddToDB.DBInfo;
import com.example.firesecure.View_and_Presenter.ChoseData;
import com.example.firesecure.View_and_Presenter.ChoseFloor;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Server {

    public final int PORT = 3000;

    public Activity activity;
    public Context context;
    public DBInfo dbInfo;
    public TextView command,
            status_connect;
    public Button turn_on_socket;
    public ServerSocket serverSocket = null;
    private Thread thread = null;
    public boolean flag;
    private Timer timer = null;
    private myTimerTask timerTask = null;
    private String delimiter = "_-SOME_DELIMITER-_";

    public Server(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        dbInfo = new DBInfo();
        command = activity.findViewById(R.id.command);
        status_connect = activity.findViewById(R.id.status_connect);
        turn_on_socket = activity.findViewById(R.id.turn_on_socket);
    }

    public void openServer() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(PORT);

                    timer = new Timer();
                    timerTask = new myTimerTask(activity);
                    timer.schedule(timerTask, 500, 1000);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            turn_on_socket.setText("Отключить");
                        }
                    });
                    flag = true;
                    Socket socket = serverSocket.accept();
                    timer.cancel();
                    timerTask.cancel();

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            status_connect.setText("Accepted - " + socket.getInetAddress());
                        }
                    });

                    try (InputStream in = socket.getInputStream();
                         OutputStream out = socket.getOutputStream()) {

                        byte[] buf = new byte[32 * 1024];
                        int readBytes = in.read(buf);

                        String line = new String(buf, 0, readBytes);
                        out.write(processQuery(line).getBytes());
                        out.flush();

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                command.setVisibility(View.VISIBLE);
                                status_connect.setVisibility(View.GONE);
                                command.setText("Запрос: " + line);
                            }
                        });

                        final Gson gson = new Gson();

                        byte[] buf_2 = new byte[32 * 1024];
                        int readBytes_2 = in.read(buf_2);

                        String line_2 = new String(buf_2, 0, readBytes_2);
                        ArrayList<String> arrayList = gson.fromJson(line_2, ArrayList.class);
                        fillDB(line, arrayList);
                        out.write("Всё нормально".getBytes());
                        out.flush();
                    }
                } catch (IOException e) {
                    Log.d("TAGER", e.toString());
                } catch (NullPointerException e) {
                    activity.finish();
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        command.setVisibility(View.GONE);
                        status_connect.setVisibility(View.VISIBLE);
                        turn_on_socket.setText("Включить");
                        status_connect.setText("Сервер отключён");
                    }
                });
                closeServer();
            }
        });
        thread.start();
    }

    public String processQuery(String line) {
        if (line.equals("FireDivision")) {
            return "Запрос принят";
        } else if (line.equals("FireHydrant")) {
            ArrayList<String> num_divis = new ArrayList<>();
            ArrayList<String> id_divis = new ArrayList<>();
            ChoseDivisionDatabase divisDB = new ChoseDivisionDatabase(context);
            Cursor cursor = divisDB.readAllData();
            if (cursor.getCount() == 0) {
                num_divis.add("Нет данных. Для начала добавьте пожарную часть.");
            } else {
                final Gson gson = new Gson();
                while (cursor.moveToNext()) {
                    id_divis.add(cursor.getString(0));
                    num_divis.add(cursor.getString(1));
                }
                String id_divis_line = gson.toJson(id_divis);
                String num_divis_line = gson.toJson(num_divis);
                return id_divis_line + delimiter + num_divis_line;
            }
        } else if (line.equals("PushOfHydrant")) {
            return "Запрос принят";
        } else if (line.equals("FirePool")) {
            ArrayList<String> num_divis = new ArrayList<>();
            ArrayList<String> id_divis = new ArrayList<>();
            ChoseDivisionDatabase divisDB = new ChoseDivisionDatabase(context);
            Cursor cursor = divisDB.readAllData();
            if (cursor.getCount() == 0) {
                num_divis.add("Нет данных. Для начала добавьте пожарную часть.");
            } else {
                final Gson gson = new Gson();
                while (cursor.moveToNext()) {
                    id_divis.add(cursor.getString(0));
                    num_divis.add(cursor.getString(1));
                }
                String id_divis_line = gson.toJson(id_divis);
                String num_divis_line = gson.toJson(num_divis);
                return id_divis_line + delimiter + num_divis_line;
            }
        } else if (line.equals("EmployeeOfDivision")) {
            ArrayList<String> num_divis = new ArrayList<>();
            ArrayList<String> id_divis = new ArrayList<>();
            ChoseDivisionDatabase divisDB = new ChoseDivisionDatabase(context);
            Cursor cursor = divisDB.readAllData();
            if (cursor.getCount() == 0) {
                num_divis.add("Нет данных. Для начала добавьте пожарную часть.");
            } else {
                final Gson gson = new Gson();
                while (cursor.moveToNext()) {
                    id_divis.add(cursor.getString(0));
                    num_divis.add(cursor.getString(1));
                }
                String id_divis_line = gson.toJson(id_divis);
                String num_divis_line = gson.toJson(num_divis);
                return id_divis_line + delimiter + num_divis_line;
            }
        } else if (line.equals("TechOfDivision")) {
            ArrayList<String> num_divis = new ArrayList<>();
            ArrayList<String> id_divis = new ArrayList<>();
            ChoseDivisionDatabase divisDB = new ChoseDivisionDatabase(context);
            Cursor cursor = divisDB.readAllData();
            if (cursor.getCount() == 0) {
                num_divis.add("Нет данных. Для начала добавьте пожарную часть.");
            } else {
                final Gson gson = new Gson();
                while (cursor.moveToNext()) {
                    id_divis.add(cursor.getString(0));
                    num_divis.add(cursor.getString(1));
                }
                String id_divis_line = gson.toJson(id_divis);
                String num_divis_line = gson.toJson(num_divis);
                return id_divis_line + delimiter + num_divis_line;
            }
        } else if (line.equals("BuildingOfDivision")) {
            ArrayList<String> num_divis = new ArrayList<>();
            ArrayList<String> id_divis = new ArrayList<>();
            ChoseDivisionDatabase divisDB = new ChoseDivisionDatabase(context);
            Cursor cursor = divisDB.readAllData();
            if (cursor.getCount() == 0) {
                num_divis.add("Нет данных. Для начала добавьте пожарную часть.");
            } else {
                final Gson gson = new Gson();
                while (cursor.moveToNext()) {
                    id_divis.add(cursor.getString(0));
                    num_divis.add(cursor.getString(1));
                }
                String id_divis_line = gson.toJson(id_divis);
                String num_divis_line = gson.toJson(num_divis);
                return id_divis_line + delimiter + num_divis_line;
            }
        } else if (line.equals("FloorOfBuilding")) {
            ArrayList<String> id_building = new ArrayList<>();
            ArrayList<String> name_building = new ArrayList<>();
            ChoseBuildingDatabase buildingDB = new ChoseBuildingDatabase(context);
            Cursor cursor = buildingDB.readAllData();
            if (cursor.getCount() == 0) {
                name_building.add("Нет данных. Для начала добавьте здание.");
            } else {
                final Gson gson = new Gson();
                while (cursor.moveToNext()) {
                    id_building.add(cursor.getString(0));
                    name_building.add(cursor.getString(1));
                }
                String id_building_line = gson.toJson(id_building);
                String name_building_line = gson.toJson(name_building);
                return id_building_line + delimiter + name_building_line;
            }
        } else if (line.equals("RoomOfFloor")) {
            ArrayList<String> id_floor = new ArrayList<>();
            ArrayList<String> num_floor = new ArrayList<>();
            ArrayList<String> id_building_of_floor = new ArrayList<>();
            String mainPath = "";
            ChoseFloorDatabase floorDB = new ChoseFloorDatabase(context);
            Cursor floorCursor = floorDB.readAllData();
            if (floorCursor.getCount() == 0) {
                num_floor.add("Нет данных. Для начала добавьте здание.");
            } else {
                final Gson gson = new Gson();
                while (floorCursor.moveToNext()) {
                    id_floor.add(floorCursor.getString(0));
                    num_floor.add(floorCursor.getString(1));
                    id_building_of_floor.add(floorCursor.getString(6));
                }
                String id_floor_line = gson.toJson(id_floor);
                String num_floor_line = gson.toJson(num_floor);
                String id_building_of_floor_line = gson.toJson(id_building_of_floor);
                mainPath = id_floor_line + delimiter + num_floor_line + delimiter + id_building_of_floor_line + delimiter;
            }

            ArrayList<String> id_building = new ArrayList<>();
            ArrayList<String> name_building = new ArrayList<>();

            ChoseBuildingDatabase buildingDB = new ChoseBuildingDatabase(context);
            Cursor buildingCursor = buildingDB.readAllData();
            if (buildingCursor.getCount() == 0) {
                name_building.add("Нет данных. Для начала добавьте здание.");
            } else {
                final Gson gson = new Gson();
                while (buildingCursor.moveToNext()) {
                    id_building.add(buildingCursor.getString(0));
                    name_building.add(buildingCursor.getString(1));
                }
                String id_building_line = gson.toJson(id_building);
                String name_building_line = gson.toJson(name_building);
                mainPath = mainPath + id_building_line + delimiter + name_building_line;
                return mainPath;
            }
        } else if (line.equals("Materials")) {
            ArrayList<String> id_building = new ArrayList<>();
            ArrayList<String> name_building = new ArrayList<>();
            ChoseBuildingDatabase buildingDB = new ChoseBuildingDatabase(context);
            Cursor cursor = buildingDB.readAllData();
            if (cursor.getCount() == 0) {
                name_building.add("Нет данных. Для начала добавьте здание.");
            } else {
                final Gson gson = new Gson();
                while (cursor.moveToNext()) {
                    id_building.add(cursor.getString(0));
                    name_building.add(cursor.getString(1));
                }
                String id_building_line = gson.toJson(id_building);
                String name_building_line = gson.toJson(name_building);
                return id_building_line + delimiter + name_building_line;
            }
        }
        return "Ошибка";
    }

    public void fillDB(String line, ArrayList<String> arrayList) {
        if (line.equals("FireDivision")) {
            addDivisionToDB(arrayList.get(0), arrayList.get(1), arrayList.get(2));
        } else if (line.equals("FireHydrant")) {
            addHydrantToDB(arrayList);
        } else if (line.equals("PushOfHydrant")) {
            addPushOfHydrantToDB(arrayList);
        } else if (line.equals("FirePool")) {
            addFirePoolToDB(arrayList);
        } else if (line.equals("EmployeeOfDivision")) {
            addEmployeeOfDivisionToDB(arrayList);
        } else if (line.equals("TechOfDivision")) {
            addTechOfDivisionToDB(arrayList);
        } else if (line.equals("BuildingOfDivision")) {
            addBuildingOfDivisionToDB(arrayList);
        } else if (line.equals("FloorOfBuilding")) {
            addFloorOfBuildingToDB(arrayList);
        } else if (line.equals("RoomOfFloor")) {
            addRoomOfFloorToDB(arrayList);
        } else if (line.equals("Materials")) {
            addMaterialsToDB(arrayList);
        }
    }

    public void addDivisionToDB(String one, String two, String three) {
        try {
            ChoseDivisionDatabase myDB = new ChoseDivisionDatabase(context);
            myDB.addDivision(one, two, three);
        } catch (Exception e) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Добавлено успешно", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void addHydrantToDB(ArrayList<String> arrayList) {
        try {
            ChoseHydrantDatabase myDB = new ChoseHydrantDatabase(context);
            myDB.addHydrant(arrayList);
        } catch (Exception e) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Добавлено успешно", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void addPushOfHydrantToDB(ArrayList<String> arrayList) {
        try {
            ChosePushOfHydrantDatabase myDB = new ChosePushOfHydrantDatabase(context);
            myDB.addPush(arrayList);
        } catch (Exception e) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Добавлено успешно", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void addFirePoolToDB(ArrayList<String> arrayList) {
        try {
            ChosePoolDatabase myDB = new ChosePoolDatabase(context);
            myDB.addPool(arrayList);
        } catch (Exception e) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Добавлено успешно", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void addEmployeeOfDivisionToDB(ArrayList<String> arrayList) {
        try {
            ChoseEmployeeDatabase myDB = new ChoseEmployeeDatabase(context);
            myDB.addEmployee(arrayList);
        } catch (Exception e) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Добавлено успешно", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void addTechOfDivisionToDB(ArrayList<String> arrayList) {
        try {
            ChoseTechDatabase myDB = new ChoseTechDatabase(context);
            myDB.addTech(arrayList);
        } catch (Exception e) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Добавлено успешно", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void addBuildingOfDivisionToDB(ArrayList<String> arrayList) {
        try {
            ChoseBuildingDatabase myDB = new ChoseBuildingDatabase(context);
            myDB.addMainBuildingInfo(arrayList.get(0), arrayList.get(1), arrayList.get(2), arrayList.get(3));
        } catch (Exception e) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Добавлено успешно", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    public void addFloorOfBuildingToDB(ArrayList<String> arrayList) {
        try {
            ChoseFloorDatabase myDB = new ChoseFloorDatabase(context);
            myDB.addFloor(arrayList);
        } catch (Exception e) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Добавлено успешно", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void addRoomOfFloorToDB(ArrayList<String> arrayList) {
        try {
            ChoseRoomDatabase myDB = new ChoseRoomDatabase(context);
            myDB.addRoom(arrayList);
        } catch (Exception e) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Добавлено успешно", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void addMaterialsToDB(ArrayList<String> arrayList) {
        try {
            ChoseDataDatabase myDB = new ChoseDataDatabase(context);
            myDB.addData(arrayList);
        } catch (Exception e) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Добавлено успешно", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public boolean getFlag() {
        return flag;
    }

    public void closeServer() {
        if (thread != null && serverSocket != null && timer != null && timerTask != null) {
            try {
                flag = false;
                timerTask.cancel();
                timerTask = null;
                timer.cancel();
                timer = null;
                serverSocket.close();
                serverSocket = null;
                thread.interrupt();
                thread = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class myTimerTask extends TimerTask {

    public Activity activity;
    public TextView status_connect;
    public Button turn_on_socket;
    public StringBuilder start;
    private int index = 0;

    public myTimerTask(Activity activity) {
        this.activity = activity;
        status_connect = activity.findViewById(R.id.status_connect);
        turn_on_socket = activity.findViewById(R.id.turn_on_socket);
        start = new StringBuilder("Сервер открыт, ожидаем отправки данных");
    }

    @Override
    public void run() {
        start.append(".");
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                status_connect.setText(start.toString());
            }
        });
        index++;
        if (index > 3) {
            index = 0;
            start = new StringBuilder("Сервер открыт, ожидаем отправки данных");
        }
    }
}
