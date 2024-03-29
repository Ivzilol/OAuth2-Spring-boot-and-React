import {useEffect, useState} from "react";
import baseURL from "../baseUrl/BaseURL";

const Products = () => {

    const [products, setProducts] = useState(null);

    const getProducts = async () => {
        const response = await fetch(`${baseURL}products`, {
            method: "GET",
            redirect: "follow",
            credentials: 'include'
        });
        if(response.redirected) {
            document.location = `${baseURL}login`
        }
        const data = await response.json();
        setProducts(data);
    }

    useEffect(() => {
        getProducts()
    }, [])




    return(
        <main>
            <section className="products-container">
                <h3>Products</h3>
                {products ?
                <div className="products-container-items">
                    {products.map((product) => (
                        <div>
                            <p>Product name: {product.name}</p>
                            <p>Product price: {product.price}</p>
                            <p>Product category: {product.category}</p>
                        </div>
                    ))}
                </div>
                    :
                    <></>
                }
            </section>
        </main>
    )


}

export default Products;