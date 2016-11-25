INSERT INTO %s SELECT DISTINCT r.region_name as region, c.item_name as country , m.item_name as municipality, sn.street_name as street_name, pr.property_registration_number as building_number from region r
join country c on c.region_id = r.region_id
join municipality m on m.country_id = c.object_id
join street_name sn on sn.municipality_id = m.municipality_id
join building_number bn on bn.street_name_id = sn.street_id
join property_registration_number pr on pr.property_registration_id = bn.property_registration_id
WHERE
    (datediff(day, getdate(), r.valid_to) > 0 or r.valid_to is NULL) and (datediff(day, r.valid_from, getdate() ) > 0 or r.valid_from is NULL)
and (datediff(day, getdate(), c.valid_to) > 0 or c.valid_to is NULL ) and (datediff(day, c.valid_from ,getdate()) > 0 or c.valid_from is NULL )
and (datediff(DAY, getdate(), m.valid_to) > 0 or m.valid_to is NULL ) and (datediff(DAY, m.valid_from, getdate() ) > 0 or m.valid_from is null)
and (datediff(DAY, getdate(), sn.valid_to) > 0 or sn.valid_to is NULL ) and (datediff(DAY, sn.valid_from, getdate()) > 0 or sn.valid_from is NULL )
and (datediff(DAY, getdate(), bn.valid_to) > 0 or bn.valid_to is NULL ) and (datediff(DAY, bn.valid_from, getdate()) > 0 or bn.valid_from is NULL )
and (datediff(DAY, getdate(), pr.valid_to) > 0 or pr.valid_to is NULL ) and (datediff(DAY, pr.valid_from, getdate()) > 0 or pr.valid_from is NULL )
