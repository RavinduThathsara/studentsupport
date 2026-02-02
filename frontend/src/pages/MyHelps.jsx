import { useEffect, useState } from "react";
import api from "../api/axios";
import RequestCard from "../components/RequestCard";

export default function MyHelps() {
  const meId = Number(localStorage.getItem("userId"));
  const [data, setData] = useState([]);

  useEffect(() => {
    const load = async () => {
      const res = await api.get("/api/requests");
      setData(res.data.filter(r => r.helperId === meId));
    };
    load();
  }, [meId]);

  return (
    <div style={{ maxWidth: 900, margin: "16px auto", padding: 12 }}>
      <h2>Requests I Help With</h2>
      {data.map(r => <RequestCard key={r.id} r={r} />)}
    </div>
  );
}
