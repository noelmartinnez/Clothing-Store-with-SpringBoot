CREATE TABLE IF NOT EXISTS Usuario (
    Id SERIAL PRIMARY KEY,
    Email VARCHAR(255) NOT NULL,
    Nombre VARCHAR(255) NOT NULL,
    Apellidos VARCHAR(255),
    Password VARCHAR(255) NOT NULL,
    Telefono VARCHAR(20),
    Codigopostal INTEGER,
    Pais VARCHAR(255),
    Poblacion VARCHAR(255),
    Direccion VARCHAR(255),
    Admin BOOLEAN
);

CREATE TABLE IF NOT EXISTS Categoria (
    Id SERIAL PRIMARY KEY,
    Nombre VARCHAR(255) NOT NULL,
    Descripcion VARCHAR(255),
    SubcategoriaId INT,
    FOREIGN KEY (SubcategoriaId) REFERENCES Categoria(Id)
);

CREATE TABLE IF NOT EXISTS Producto (
    Id SERIAL PRIMARY KEY,
    Nombre VARCHAR(255) NOT NULL,
    Precio FLOAT NOT NULL,
    Stock INT NOT NULL,
    NumRef VARCHAR(255),
    Destacado BOOLEAN,
    CategoriaId INT,
    Img VARCHAR(255),
    FOREIGN KEY (CategoriaId) REFERENCES Categoria(Id)
);

CREATE TABLE IF NOT EXISTS Carrito (
    Id SERIAL PRIMARY KEY,
    UsuarioId INT,
    FOREIGN KEY (UsuarioId) REFERENCES Usuario(Id)
);

CREATE TABLE IF NOT EXISTS LineaCarrito (
    Id SERIAL PRIMARY KEY,
    Cantidad INT,
    ProductoId INT,
    CarritoId INT,
    FOREIGN KEY (ProductoId) REFERENCES Producto(Id),
    FOREIGN KEY (CarritoId) REFERENCES Carrito(Id)
);

CREATE TABLE IF NOT EXISTS Comentario (
    Id SERIAL PRIMARY KEY,
    Descripcion VARCHAR(255),
    Fecha DATE,
    UsuarioId INT,
    ProductoId INT,
    FOREIGN KEY (UsuarioId) REFERENCES Usuario(Id),
    FOREIGN KEY (ProductoId) REFERENCES Producto(Id)
);

CREATE TABLE IF NOT EXISTS Pedido (
    Id SERIAL PRIMARY KEY,
    Fecha DATE,
    UsuarioId INT,
    FOREIGN KEY (UsuarioId) REFERENCES Usuario(Id)
);

CREATE TABLE IF NOT EXISTS LineaPedido (
     Id SERIAL PRIMARY KEY,
     Fecha DATE,
     Cantidad INT,
     Precio FLOAT,
     PedidoId INT,
     ProductoId INT,
     FOREIGN KEY (PedidoId) REFERENCES Pedido(Id),
     FOREIGN KEY (ProductoId) REFERENCES Producto(Id)
);

-- Seeder para la tabla Usuario
INSERT INTO Usuario (Email, Nombre, Apellidos, Password, Telefono, Codigopostal, Pais, Poblacion, Direccion, Admin)
VALUES
    ('usuario1@example.com', 'Usuario1', 'Apellidos1', 'contraseña1', '123456789', 12345, 'País1', 'Población1', 'Dirección1', false),
    ('usuario2@example.com', 'Usuario2', 'Apellidos2', 'contraseña2', '987654321', 54321, 'País2', 'Población2', 'Dirección2', false),
    ('usuario3@example.com', 'Usuario3', 'Apellidos3', 'contraseña3', '456789012', 67890, 'País3', 'Población3', 'Dirección3', false),
    ('usuario4@example.com', 'Usuario4', 'Apellidos4', 'contraseña4', '321098765', 54321, 'País4', 'Población4', 'Dirección4', false),
    ('usuario5@example.com', 'Usuario5', 'Apellidos5', 'contraseña5', '876543210', 98765, 'País5', 'Población5', 'Dirección5', false),
    ('admin@admin.com', 'admin', 'admin', '123456', '123456789', 98785, 'País6', 'Población6', 'Dirección6', true);

-- Seeder para la tabla Categoria
INSERT INTO Categoria (Nombre, Descripcion, SubcategoriaId)
VALUES
    ('Ropa de mujer', 'Ropa de moda de mujer', NULL),
    ('Ropa de hombre', 'Ropa de moda de hombre', NULL),
    ('Accesorios', 'Accesorios de moda', 1), -- Subcategoría de Ropa de mujer
    ('Ropa infantil para mujer', 'Ropa para niñas y bebés', 1),
    ('Complementos para mujer', 'Complementos de moda para mujeres', 1),
    ('Ropa infantil para hombre', 'Ropa para niños y bebés', 2),
    ('Complementos para hombre', 'Complementos de moda para hombres', 2),
    ('Zapatos para mujer', 'Calzado de moda para mujeres', 1),
    ('Zapatos para hombre', 'Calzado de moda para hombres', 2),
    ('Bisutería para mujer', 'Bisutería y joyería para mujeres', 1),
    ('Relojes para hombre', 'Relojes de moda para hombres', 2);


-- Seeder para la tabla Producto
INSERT INTO Producto (Nombre, Precio, Stock, NumRef, Destacado, CategoriaId, Img)
VALUES
    ('Adidas', 59.99, 50, '123456789', true, 2, 'adidas.jpg'),
    ('Camiseta Verde', 12.99, 30, '987654321', false, 2, 'camiseta_verde.jpg'),
    ('Gorra de Moda', 19.99, 100, '567890123', true, 4, 'gorra.jpg'), -- Asociado a la categoría de Accesorios
    ('Falda Elegante', 49.99, 80, '456789012', false, 4, 'faldaMujer.jpg'), -- Asociado a la categoría de Accesorios
    ('Falda Casual', 19.99, 70, '345678901', false, 4, 'faldaHombre.jpg'),
    ('Chandal', 39.99, 70, '345674901', true, 2, 'chandal.jpg'),
    ('Conjunto', 19.99, 70, '343678901', false, 1, 'conjunto.jpg'),
    ('Futbol', 79.99, 70, '743678901', true, 2, 'futbol.jpg'),
    ('Jersey', 59.99, 70, '343858901', false, 2, 'jersey.jpg'),
    ('Nike', 29.99, 70, '843678901', false, 1, 'nike.jpg');

-- Seeder para la tabla Carrito
INSERT INTO Carrito (UsuarioId)
VALUES
    (1), -- Asociado al Usuario con Id 1
    (2), -- Asociado al Usuario con Id 2
    (3), -- Asociado al Usuario con Id 3
    (4), -- Asociado al Usuario con Id 4
    (5); -- Asociado al Usuario con Id 5

-- Seeder para la tabla LineaCarrito
INSERT INTO LineaCarrito (Cantidad, ProductoId, CarritoId)
VALUES
    (2, 1, 1), -- 2 unidades del Producto con Id 1 en el Carrito con Id 1
    (1, 2, 2), -- 1 unidad del Producto con Id 2 en el Carrito con Id 2
    (3, 3, 3), -- 3 unidades del Producto con Id 3 en el Carrito con Id 3
    (1, 5, 1), -- 1 unidad del Producto con Id 5 en el Carrito con Id 1
    (2, 5, 2); -- 2 unidades del Producto con Id 6 en el Carrito con Id 2

-- Seeder para la tabla Comentario
INSERT INTO Comentario (Descripcion, Fecha, UsuarioId, ProductoId)
VALUES
    ('Excelente calidad y ajuste perfecto', '2023-01-01', 1, 1),
    ('Un clásico atemporal, me encanta', '2023-01-03', 2, 1),
    ('¡Buena relación calidad-precio!', '2023-01-05', 1, 2),
    ('Muy cómodo y elegante', '2023-01-07', 3, 3),
    ('La textura del material es increíble', '2023-01-10', 4, 3),
    ('Estilo moderno y juvenil', '2023-01-12', 2, 4),
    ('Buena compra, envío rápido', '2023-01-15', 3, 4),
    ('Gran oferta, lo recomiendo', '2023-01-18', 4, 5),
    ('El diseño es único, me encanta', '2023-01-20', 5, 5),
    ('Calidad sorprendente', '2023-01-22', 1, 6),
    ('Perfecto para cualquier ocasión', '2023-01-25', 2, 7),
    ('¡No puedo dejar de usarlo!', '2023-01-28', 3, 7),
    ('Mejor de lo esperado', '2023-01-30', 4, 8),
    ('Elegante y versátil', '2023-02-02', 5, 9),
    ('Increíble, definitivamente compraré más', '2023-02-05', 1, 10),
    ('El color es exactamente como se muestra', '2023-02-08', 2, 10);

-- Seeder para la tabla Pedido
INSERT INTO Pedido (Fecha, UsuarioId)
VALUES
    ('2023-01-03', 1), -- Pedido del Usuario con Id 1
    ('2023-01-04', 2), -- Pedido del Usuario con Id 2
    ('2023-01-07', 3); -- Pedido del Usuario con Id 3

-- Seeder para la tabla LineaPedido
INSERT INTO LineaPedido (Fecha, Cantidad, Precio, PedidoId, ProductoId)
VALUES
    ('2023-01-03', 2, 1199.98, 1, 1), -- Línea de pedido para el Producto con Id 1 en el Pedido con Id 1
    ('2023-01-04', 1, 1299.99, 2, 2), -- Línea de pedido para el Producto con Id 2 en el Pedido con Id 2
    ('2023-01-07', 1, 19.99, 3, 4), -- Línea de pedido para el Producto con Id 4 en el Pedido con Id 3
    ('2023-01-07', 2, 59.98, 3, 2); -- Línea de pedido para el Producto con Id 6 en el Pedido con Id 3