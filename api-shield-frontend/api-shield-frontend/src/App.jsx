import { useState } from "react";

function App() {
  const [apiKey, setApiKey] = useState("");
  const [response, setResponse] = useState("");
  const [logs, setLogs] = useState([]);

  const callApi = async () => {
    try {
      const res = await fetch(`http://localhost:8080/api/test?apiKey=${apiKey}`);
      const data = await res.text();
      setResponse(data);
    } catch (err) {
      setResponse("Error calling API");
    }
  };

  const fetchLogs = async () => {
    const res = await fetch("http://localhost:8080/api/logs");
    const data = await res.json();
    setLogs(data);
  };

  return (
    <div style={{ padding: "30px", fontFamily: "Arial" }}>
      <h1>🚀 API Shield Dashboard</h1>

      <input
        type="text"
        placeholder="Enter API Key"
        value={apiKey}
        onChange={(e) => setApiKey(e.target.value)}
      />

      <button onClick={callApi} style={{ marginLeft: "10px" }}>
        Call API
      </button>

      <h3>Response:</h3>
      <p>{response}</p>

      <hr />

      <button onClick={fetchLogs}>View Logs</button>

      <ul>
        {logs.map((log, index) => (
          <li key={index}>{log}</li>
        ))}
      </ul>
    </div>
  );
}

export default App;