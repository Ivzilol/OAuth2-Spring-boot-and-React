import './App.css';
import {Route, Routes} from "react-router-dom";
import Products from "./components/products/Products";

function App() {

  return (
    <div className="App">
      <Routes>
          <Route path="/products" element={<Products/>}/>
      </Routes>
    </div>
  );
}

export default App;
