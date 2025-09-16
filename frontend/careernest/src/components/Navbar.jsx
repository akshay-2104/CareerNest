import { Link } from "react-router-dom";

function Navbar() {
  return (
    <nav style={{ padding: "10px", background: "#282c34", color: "white" ,width:"100%"}}>
      <h2>CareerNest</h2>
      <ul style={{ display: "flex", listStyle: "none", gap: "20px" }}>
        <li><Link to="/" style={{ color: "white", textDecoration: "none" }}>Home</Link></li>
        <li><Link to="/about" style={{ color: "white", textDecoration: "none" }}>About</Link></li>
        <li><Link to="/login" style={{ color: "white", textDecoration: "none" }}>Login</Link></li>
      </ul>
    </nav>
  );
}

export default Navbar;
