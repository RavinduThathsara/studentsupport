import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import api from "../api/axios";

export default function Register() {
  const nav = useNavigate();

  const [fullName, setFullName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [err, setErr] = useState("");
  const [loading, setLoading] = useState(false);

  const submit = async (e) => {
    e.preventDefault();
    setErr("");

    const payload = {
      fullName: fullName.trim(),
      email: email.trim(),
      password: password.trim(),
    };

    if (!payload.fullName || !payload.email || !payload.password) {
      setErr("Please fill all fields.");
      return;
    }

    try {
      setLoading(true);

      // ✅ With Vite proxy + axios baseURL "/api", this becomes:
      // http://localhost:5173/api/auth/register  -> forwarded to backend 8080
      await api.post("/auth/register", payload);

      nav("/login");
    } catch (e2) {
      const msg =
        e2?.response?.data?.error ||
        e2?.response?.data?.message ||
        (Array.isArray(e2?.response?.data?.errors)
          ? e2.response.data.errors[0]?.msg ||
            e2.response.data.errors[0]?.defaultMessage ||
            e2.response.data.errors[0]?.message
          : null) ||
        e2?.message ||
        "Register failed";

      setErr(msg);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: 420, margin: "24px auto", width: "100%" }}>
      <h2>Register</h2>

      {err && <p style={{ color: "red" }}>{err}</p>}

      <form onSubmit={submit}>
        <input
          placeholder="Full Name"
          value={fullName}
          onChange={(e) => setFullName(e.target.value)}
          style={{ width: "100%", padding: 10, marginBottom: 10 }}
        />

        <input
          placeholder="Email"
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          style={{ width: "100%", padding: 10, marginBottom: 10 }}
        />

        <input
          placeholder="Password"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          style={{ width: "100%", padding: 10, marginBottom: 10 }}
        />

        <button
          type="submit"
          disabled={loading}
          style={{ padding: 10, width: "100%", opacity: loading ? 0.7 : 1 }}
        >
          {loading ? "Creating..." : "Create account"}
        </button>
      </form>

      <p style={{ marginTop: 10 }}>
        Already have account? <Link to="/login">Login</Link>
      </p>
    </div>
  );
}
