#### Software Architecture and Platforms - a.y. 2025-2026

## Lab Activity #01 - 20250926  

The system has been rebuils as a fully MVC system.
The M,V,C components have been decoupled from eachother by using interfaces (bbbom.interfaces package) and (manual) dependency injection pattern to gurantee isolation and interoperability of the components.
Mulitple UIs and Model implementation have been developed thus enabling to really test the indipendency of component by swapping them freely.
Also the concurrecy have been handled and multiple user interfaces (swig gui, cli, and a minimal socket interface) can coexists together.