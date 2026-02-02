import { Link } from "react-router-dom";

export default function RequestCard({ r }) {
  return (
    <div style={{ border: "1px solid #ddd", padding: 12, borderRadius: 8, marginBottom: 10 }}>
      <h3 style={{ margin: 0 }}>{r.title}</h3>
      <p style={{ margin: "6px 0" }}>
        <b>Category:</b> {r.category} | <b>Status:</b> {r.status}
      </p>
      <p style={{ margin: "6px 0", color: "#444" }}>{r.description}</p>
      <Link to={`/requests/${r.id}`}>View Details</Link>
    </div>
  );
}
