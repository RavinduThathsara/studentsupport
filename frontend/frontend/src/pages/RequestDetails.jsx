import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../api/axios";

export default function RequestDetails() {
  const { id } = useParams();
  const meId = Number(localStorage.getItem("userId"));

  const [r, setR] = useState(null);
  const [messages, setMessages] = useState([]);
  const [text, setText] = useState("");
  const [err, setErr] = useState("");

  const [stars, setStars] = useState(5);
  const [comment, setComment] = useState("");
  const [reportReason, setReportReason] = useState("");

  const load = async () => {
    setErr("");
    try {
      const rr = await api.get(`/api/requests/${id}`);
      setR(rr.data);

      // messages only for requester/helper; if forbidden ignore
      try {
        const mm = await api.get(`/api/requests/${id}/messages`);
        setMessages(mm.data);
      } catch {}
    } catch (e) {
      setErr("Failed to load request");
    }
  };

  useEffect(() => { load(); }, [id]);

  const accept = async () => {
    await api.post(`/api/requests/${id}/accept`);
    load();
  };

  const changeStatus = async (status) => {
    await api.post(`/api/requests/${id}/status?status=${status}`);
    load();
  };

  const sendMsg = async () => {
    if (!text.trim()) return;
    await api.post(`/api/requests/${id}/messages`, { text });
    setText("");
    load();
  };

  const rate = async () => {
    await api.post(`/api/requests/${id}/rating`, { stars, comment });
    alert("Rated!");
  };

  const report = async () => {
    await api.post(`/api/reports`, { requestId: Number(id), reason: reportReason });
    setReportReason("");
    alert("Reported!");
  };

  if (err) return <div style={{ padding: 12 }}>{err}</div>;
  if (!r) return <div style={{ padding: 12 }}>Loading...</div>;

  const isOwner = r.requesterId === meId;
  const isHelper = r.helperId === meId;

  return (
    <div style={{ maxWidth: 900, margin: "16px auto", padding: 12 }}>
      <h2>{r.title}</h2>
      <p><b>Category:</b> {r.category} | <b>Status:</b> {r.status}</p>
      <p>{r.description}</p>

      {r.status === "OPEN" && !isOwner && (
        <button onClick={accept}>Accept this request</button>
      )}

      {(isOwner || isHelper) && (
        <div style={{ marginTop: 12 }}>
          <b>Change Status:</b>{" "}
          <button onClick={() => changeStatus("IN_PROGRESS")}>IN_PROGRESS</button>{" "}
          <button onClick={() => changeStatus("COMPLETED")}>COMPLETED</button>
        </div>
      )}

      <hr style={{ margin: "18px 0" }} />

      <h3>Messages</h3>
      {messages.length === 0 ? (
        <p>Messages visible only to requester/helper (after accept).</p>
      ) : (
        <div style={{ border: "1px solid #ddd", padding: 10, borderRadius: 8 }}>
          {messages.map((m) => (
            <div key={m.id} style={{ marginBottom: 8 }}>
              <b>{m.sender?.email || "User"}:</b> {m.text}
            </div>
          ))}
        </div>
      )}

      {(isOwner || isHelper) && (
        <div style={{ marginTop: 10 }}>
          <input value={text} onChange={(e) => setText(e.target.value)} placeholder="Type message..." style={{ width: "75%", padding: 10 }} />
          <button onClick={sendMsg} style={{ padding: 10, marginLeft: 8 }}>Send</button>
        </div>
      )}

      <hr style={{ margin: "18px 0" }} />

      <h3>Rate Helper (Requester only, after COMPLETED)</h3>
      <div style={{ display: "flex", gap: 8, alignItems: "center" }}>
        <select value={stars} onChange={(e) => setStars(Number(e.target.value))}>
          {[1,2,3,4,5].map(n => <option key={n} value={n}>{n}</option>)}
        </select>
        <input value={comment} onChange={(e) => setComment(e.target.value)} placeholder="Comment (optional)" style={{ flex: 1, padding: 10 }} />
        <button onClick={rate}>Submit Rating</button>
      </div>

      <hr style={{ margin: "18px 0" }} />

      <h3>Report this Request</h3>
      <div style={{ display: "flex", gap: 8 }}>
        <input value={reportReason} onChange={(e) => setReportReason(e.target.value)} placeholder="Reason" style={{ flex: 1, padding: 10 }} />
        <button onClick={report}>Report</button>
      </div>
    </div>
  );
}
