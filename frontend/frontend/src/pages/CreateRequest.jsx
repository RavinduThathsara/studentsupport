import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";

export default function CreateRequest() {
  const nav = useNavigate();
  const [title, setTitle] = useState("");
  const [category, setCategory] = useState("ACADEMIC");
  const [description, setDescription] = useState("");
  const [err, setErr] = useState("");

  const submit = async (e) => {
    e.preventDefault();
    setErr("");
    try {
      const res = await api.post("/api/requests", { title, category, description });
      nav(`/requests/${res.data.id}`);
    } catch (e2) {
      setErr(e2?.response?.data?.error || "Create failed");
    }
  };

  return (
    <div style={{ maxWidth: 700, margin: "16px auto", padding: 12 }}>
      <h2>Create Support Request</h2>
      {err && <p style={{ color: "red" }}>{err}</p>}
      <form onSubmit={submit}>
        <input value={title} onChange={(e) => setTitle(e.target.value)} placeholder="Title" style={{ width: "100%", padding: 10, marginBottom: 10 }} />
        <select value={category} onChange={(e) => setCategory(e.target.value)} style={{ width: "100%", padding: 10, marginBottom: 10 }}>
          <option value="ACADEMIC">ACADEMIC</option>
          <option value="PERSONAL">PERSONAL</option>
          <option value="CAMPUS_LIFE">CAMPUS_LIFE</option>
        </select>
        <textarea value={description} onChange={(e) => setDescription(e.target.value)} placeholder="Describe your need..." style={{ width: "100%", padding: 10, minHeight: 120, marginBottom: 10 }} />
        <button style={{ padding: 10 }}>Create</button>
      </form>
    </div>
  );
}
