import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import api from "../api/axios";

export default function Register() {
  const nav = useNavigate();
  const [fullName, setFullName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [err, setErr] = useState("");

  const submit = async (e) => {
    e.preventDefault();
    setErr("");
    try {
      const res = await api.post("/api/auth/register", { fullName, email, password });
      localStorage.setItem("token", res.data.token);
      localStorage.setItem("role", res.data.role);
      localStorage.setItem("userId", String(res.data.userId));
      nav("/");
    } catch (e2) {
      setErr(e2?.response?.data?.error || "Register failed");
    }
  };

  return (
    <div style={{ maxWidth: 420, margin: "24px auto" }}>
      <h2>Register</h2>
      {err && <p style={{ color: "red" }}>{err}</p>}
      <form onSubmit={submit}>
        <input placeholder="Full Name" value={fullName} onChange={(e) => setFullName(e.target.value)} style={{ width: "100%", padding: 10, marginBottom: 10 }} />
        <input placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} style={{ width: "100%", padding: 10, marginBottom: 10 }} />
        <input placeholder="Password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} style={{ width: "100%", padding: 10, marginBottom: 10 }} />
        <button style={{ padding: 10, width: "100%" }}>Create account</button>
      </form>
      <p style={{ marginTop: 10 }}>
        Already have account? <Link to="/login">Login</Link>
      </p>
    </div>
  );
}
