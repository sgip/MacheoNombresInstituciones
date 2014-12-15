CREATE VIEW "public"."informe_concurso_constancia_persona_inscripcion" (
    id_concurso,
    codigo_concurso,
    fecha_inscripcion_persona,
    codigo_concurso_persona,
    codigo_persona,
    numero_persona,
    nombre_persona,
    telefono_persona,
    celular_persona,
    correo_electronico_persona,
    codigo_puesto,
    codigo_jurisdiccion_02,
    nombre_jurisdiccion_02,
    numero_region_supervision,
    letra_zona_supervision,
    nombre_dependencia,
    nombre_cargo,
    codigo_categoria_rubro,
    codigo_turno,
    url_antecedente_policial,
    url_antecedente_judicial)
AS
SELECT c.id_concurso, c.codigo_concurso, cpp.fecha_inscripcion_persona,
    cpp.codigo_concurso_persona, ps.codigo_persona, ps.numero_persona,
    ps.nombre_persona, ps.telefono_persona, ps.celular_persona,
    ps.correo_electronico_persona, p.codigo_puesto, jp.codigo_jurisdiccion_02,
    jp.nombre_jurisdiccion_02, d.numero_region_supervision,
    d.letra_zona_supervision, d.nombre_dependencia, cg.nombre_cargo,
    cr.codigo_categoria_rubro, tn.codigo_turno, ps.url_antecedente_policial,
    ps.url_antecedente_judicial
FROM (((((((((((concurso c JOIN concurso_puesto cp ON ((c.id_concurso =
    cp.id_concurso))) JOIN concurso_puesto_persona cpp ON
    ((cp.id_concurso_puesto = cpp.id_concurso_puesto))) JOIN puesto p ON
    ((cp.id_puesto = p.id_puesto))) JOIN cargo cg ON ((p.id_cargo =
    cg.id_cargo))) JOIN dependencia d ON ((p.id_dependencia =
    d.id_dependencia))) JOIN persona ps ON ((cpp.id_persona = ps.id_persona)))
    JOIN concepto_puesto cpt ON ((p.id_puesto = cpt.id_puesto))) JOIN
    asignacion_presupuesto_linea apl ON ((cpt.id_asignacion_presupuesto_linea =
    apl.id_asignacion_presupuesto_linea))) JOIN turno tn ON ((p.numero_turno =
    tn.numero_turno))) LEFT JOIN jurisdiccion_plus jp ON
    ((d.id_jurisdiccion_direccion_dependencia = jp.id_jurisdiccion))) JOIN
    categoria_rubro cr ON ((apl.id_categoria_rubro = cr.id_categoria_rubro)))
ORDER BY c.id_concurso, ps.numero_persona, cpp.id_concurso_puesto_persona;
