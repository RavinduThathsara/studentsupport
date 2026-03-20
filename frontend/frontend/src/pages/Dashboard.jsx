import { useEffect, useState } from "react";
import api from "../api/axios";
import RequestCard from "../components/RequestCard";

export default function Dashboard() {
  const [data, setData] = useState([]);
  const [err, setErr] = useState("");

  useEffect(() => {
    const load = async () => {
      try {
        const res = await api.get("/requests");
        setData(res.data);
      } catch {
        setErr("Failed to load requests (Are you logged in?)");
      }
    };
    void load();
  }, []);

  return (
    <div style={{ maxWidth: 900, margin: "16px auto", padding: 12 }}>
      <h2>All Support Requests</h2>
      {err && <p style={{ color: "red" }}>{err}</p>}
      {data.map((r) => <RequestCard key={r.id} r={r} />)}
    </div>
  );
}
