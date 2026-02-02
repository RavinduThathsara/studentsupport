import { Link, useNavigate } from "react-router-dom";

export default function Navbar() {
  const nav = useNavigate();
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");

  const logout = () => {
    localStorage.clear();
    nav("/login");
  };

  return (
    <div style={{ padding: 12, borderBottom: "1px solid #ddd", display: "flex", gap: 12 }}>
      <Link to="/">Dashboard</Link>
      <Link to="/create">Create Request</Link>
      <Link to="/my-requests">My Requests</Link>
      <Link to="/my-helps">My Helps</Link>
      {role === "ADMIN" && <Link to="/admin/reports">Admin</Link>}
      <div style={{ marginLeft: "auto" }}>
        {token ? <button onClick={logout}>Logout</button> : <Link to="/login">Login</Link>}
      </div>
    </div>
  );
}
