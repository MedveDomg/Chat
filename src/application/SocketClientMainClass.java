package application;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


public class SocketClientMainClass {

	private static JTextArea ta;
	private static JButton b;
	private static JLabel l;

	private static Socket client;
	private static final DefaultListModel modelList = new DefaultListModel();
	private static final JTextField tfPort = new JTextField();
	private static final JTextField tfIP = new JTextField();


	public static int flag;
	private static JButton connect;

	public static void main(String[] args) throws UnknownHostException, IOException {

		setGUI();
		connectButtonAction();


	}

	public static void connectServer(int port, String serverName) throws IOException {

		System.out.println("Test tfPort.getText()");

		System.out.println("Connecting to " + serverName + " on port " + port);


		client = new Socket(serverName, port);

		System.out.println("Just connected to " + client.getRemoteSocketAddress());

		sendMessageToServer("Рш!");


		InputStream inFromServer = client.getInputStream();
		DataInputStream in = new DataInputStream(inFromServer);

		boolean isConnected = true;
		while (isConnected) {


			String messageFromServer = null;
			try {
				messageFromServer = in.readUTF();
			} catch (Exception e) {
				isConnected = false;
				messageFromServer = "";
			}

			System.out.println("Server says : " + messageFromServer);
//			l.setText("Server says : " + messageFromServer);
			modelList.addElement("Server: " + messageFromServer);

		}

		client.close();
	}


	public static void sendMessageToServer(String message) {

		try {

			OutputStream outToServer = client.getOutputStream();

			DataOutputStream out = new DataOutputStream(outToServer);

			out.writeUTF(message);


		} catch (Exception e) {


		}

	}

	private static void setGUI() {
		JFrame frame = new JFrame();
//		frame.setLayout(new BorderLayout());
		frame.setTitle("Client");
		frame.setSize(315, 515);
		int width = 400;
		int height = 600;


		JPanel p = new JPanel();


		JLabel lIP = new JLabel("IP: ");
		lIP.setLocation(100, 100);
		p.add(lIP);
		//		tfIP.setLayout(new FlowLayout());
		tfIP.setPreferredSize(new Dimension(90, 20));

		p.add(tfIP);
//		lIP.setLocation(width-300, height-550);


		JLabel lPort = new JLabel("Port: ");
//		lPort.setLocation(width-300, height-510);
		p.add(lPort);

		tfPort.setPreferredSize(new Dimension(50, 20));
		p.add(tfPort);

		connect = new JButton("Con");

		connect.setPreferredSize(new Dimension(70, 20));





				p.add(connect);


				frame.setVisible(true);


				JList list = new JList(modelList);

				list.setPreferredSize(new Dimension(300, 400));
				p.add(list);


//		>> TextArea
				ta = new JTextArea("Клиент");
				ta.setPreferredSize(new Dimension(150, 30));

				p.add(ta);

//		>> Butoon
				b = new JButton("Send");
				b.setPreferredSize(new Dimension(100, 30));

				b.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						String getText = ta.getText();
						sendMessageToServer(getText);
						modelList.addElement("Me: " + ta.getText());


					}
				});

				p.add(b);

//		>> Label
				l = new JLabel();

				p.add(l);

				frame.add(p);


				frame.setVisible(true);


			}
	private static void connectButtonAction(){
		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Hello");
				String portText = tfPort.getText();
				String serverName = tfIP.getText();
				Thread thread = new Thread() {
					public void run() {
						int port = Integer.parseInt(portText);
						try {
							connectServer(port,serverName);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				};
				thread.start();

			}
		});

	}


	}







