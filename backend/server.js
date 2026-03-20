import express from "express";
import cors from "cors";

const app = express();

// Legacy mock server. The real backend is the Spring Boot app in
// `backend/src/main/java`, which serves the full `/api/*` surface on port 8081.
// Keep this mock on a different port so it does not shadow those routes.

/* CORS (allow your Vite frontend) */
app.use(
    cors({
        origin: "http://localhost:5173",
        methods: ["GET", "POST", "PUT", "DELETE", "OPTIONS"],
        allowedHeaders: ["Content-Type", "Authorization"],
    })
);

/* Preflight */
app.options("*", cors());

/* Body parser */
app.use(express.json());

/* Health check */
app.get("/", (req, res) => {
    res.status(200).json({
        message: "Legacy mock backend is running.",
        note: "Use the Spring Boot backend on http://localhost:8081 for /api/requests and other application routes.",
    });
});

/* Register mock */
app.post("/api/auth/register", (req, res) => {
    const { fullName, email, password } = req.body;

    if (!fullName || !email || !password) {
        return res.status(400).json({ message: "Missing fields" });
    }

    return res.status(201).json({
        message: "registered",
        user: { fullName, email },
    });
});

/* Helpful: show if wrong method */
app.get("/api/auth/register", (req, res) => {
    res.status(200).json({ message: "Route exists. Use POST to register." });
});

const PORT = Number(process.env.PORT || 8082);
app.listen(PORT, () => {
    console.log(`Legacy mock backend running on http://localhost:${PORT}`);
    console.log("Use the Spring Boot backend on http://localhost:8081 for the full /api surface.");
});
