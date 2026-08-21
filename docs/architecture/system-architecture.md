                 FIIE APPLICATION
                       |
             +---------+---------+
             |                   |
             v                   v
        Flutter UI          User Input
             |
             v
        Spring Boot
          Backend
             |
     +-------+-------+
     |               |
     v               v
 PostgreSQL       AI Service
     |               |
     |        +------+-------+
     |        |      |      |
     |       YOLO   SAM   MiDaS
     |        |
     |        v
     |   Room Analysis
     |        |
     |        v
     |   FIIE Engine
     |        |
     |   +----+----+
     |   |         |
     | Vastu    Functional
     | Analysis Analysis
     |   |         |
     |   +----+----+
     |        |
     |        v
     |  Recommendation
     |        |
     |        v
     |  Prompt Generation
     |        |
     |        v
     | Stable Diffusion
     | + ControlNet
     |        |
     +--------+
              |
              v
        Final Result