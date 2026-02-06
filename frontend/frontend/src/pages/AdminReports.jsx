import { useEffect, useState } from "react";
import api from "../api/axios";

export default function AdminReports() {
  const [data, setData] = useState([]);
  const [err, setErr] = useState("");

  const load = async () => {
    setErr("");
    try {
      const res = await api.get("/api/admin/reports");
      setData(res.data);
    } catch {
      setErr("Only ADMIN can view reports (or backend not secured yet).");
    }
  };

  useEffect(() => { load(); }, []);

  const resolve = async (id) => {
    await api.post(`/api/admin/reports/${id}/resolve`);
    load();
  };

  return (
    <div style={{ maxWidth: 900, margin: "16px auto", padding: 12 }}>
      <h2>Admin Reports</h2>
      {err && <p style={{ color: "red" }}>{err}</p>}
      {data.map(r => (
        <div key={r.id} style={{ border: "1px solid #ddd", padding: 12, borderRadius: 8, marginBottom: 10 }}>
          <p><b>Report ID:</b> {r.id} | <b>Status:</b> {r.status}</p>
          <p><b>Reason:</b> {r.reason}</p>
          <button onClick={() => resolve(r.id)}>Resolve</button>
        </div>
      ))}
    </div>
  );
}
