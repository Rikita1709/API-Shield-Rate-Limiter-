import { useState } from "react";
import {
  Chart as ChartJS,
  ArcElement,
  Tooltip,
  Legend,
} from "chart.js";
import { Doughnut } from "react-chartjs-2";

ChartJS.register(ArcElement, Tooltip, Legend);

function App() {
  const [apiKey, setApiKey] = useState("");
  const [response, setResponse] = useState("");
  const [logs, setLogs] = useState([]);
  const [stats, setStats] = useState(null);
  const [apiKeyStats, setApiKeyStats] = useState({});

  const fetchStats = async () => {
    const res = await fetch("http://localhost:8080/api/stats");
    const data = await res.json();
    setStats(data);

    const keyRes = await fetch(
      "http://localhost:8080/api/stats/api-keys"
    );
    const keyData = await keyRes.json();

    setApiKeyStats(keyData);
  };

  const callApi = async () => {
    try {
      const res = await fetch(
        `http://localhost:8080/api/test?apiKey=${apiKey}`
      );

      const data = await res.text();

      setResponse(data);

      fetchStats();
    } catch {
      setResponse("Error calling API");
    }
  };

  const fetchLogs = async () => {
    const res = await fetch(
      "http://localhost:8080/api/logs"
    );

    const data = await res.json();

    setLogs(data);
  };

  const chartData = {
    labels: ["Allowed", "Blocked"],
    datasets: [
      {
        data: [
          stats?.allowedRequests || 0,
          stats?.blockedRequests || 0,
        ],
        backgroundColor: ["#22c55e", "#ef4444"],
      },
    ],
  };

  return (
    <div style={{ padding: "30px", fontFamily: "Arial" }}>
      <h1>🛡️ API Shield Dashboard</h1>

      <input
        type="text"
        value={apiKey}
        placeholder="Enter API Key"
        onChange={(e) => setApiKey(e.target.value)}
      />

      <button
        onClick={callApi}
        style={{ marginLeft: "10px" }}
      >
        Call API
      </button>

      <p>{response}</p>

      <button onClick={fetchStats}>
        Refresh Analytics
      </button>

      <div
        style={{
          display: "flex",
          gap: "20px",
          marginTop: "20px",
        }}
      >
        <div>
          <h3>Total Requests</h3>
          <h1>{stats?.totalRequests || 0}</h1>
        </div>

        <div>
          <h3>Allowed</h3>
          <h1>{stats?.allowedRequests || 0}</h1>
        </div>

        <div>
          <h3>Blocked</h3>
          <h1>{stats?.blockedRequests || 0}</h1>
        </div>
      </div>

      <div style={{ width: "350px", marginTop: "30px" }}>
        <Doughnut data={chartData} />
      </div>

      <div style={{ marginTop: "40px" }}>
        <h2>🏆 Top API Consumers</h2>

        <table border="1" cellPadding="10">
          <thead>
            <tr>
              <th>API Key</th>
              <th>Requests</th>
            </tr>
          </thead>

          <tbody>
            {Object.entries(apiKeyStats).map(
              ([key, count]) => (
                <tr key={key}>
                  <td>{key}</td>
                  <td>{count}</td>
                </tr>
              )
            )}
          </tbody>
        </table>
      </div>

      <div style={{ marginTop: "40px" }}>
        <button onClick={fetchLogs}>
          View Logs
        </button>

        <ul>
          {logs.map((log, index) => (
            <li key={index}>{log}</li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default App;