/**
 * The world package contains most of the classes that the game world is comprised of.
 * 
 * Each sub-package contained herein represents a class of object in the game world, and should define the following functions for that type:
 * <ul>
 * <li>the Class for the game object</li>
 * <li>random generators for the class</li>
 * <li>serialization for the class</li>
 * </ul>
 * 
 * <h2>Sub-packages</h2>
 * <table border="1">
 * <tr><th>Package    </th> <th>summary              </th> <th>status                                   </th> </tr>
 * <tr><td>core       </td> <td>shared functionality </td> <td>needs more interfaces                    </td> </tr>
 * <tr><td>levels     </td> <td>levels to play       </td> <td>needs serialization                      </td> </tr>
 * <tr><td>areas      </td> <td>areas to play in     </td> <td>needs serialization                      </td> </tr>  
 * <tr><td>tileMaps   </td> <td>static environment   </td> <td>needs serialization, fix TileMapGenerator</td> </tr>
 * <tr><td>tiles      </td> <td>pieces of environment</td> <td>needs serialization and SpriteTile       </td> </tr>
 * <tr><td>structures </td> <td>buildings in an Area </td> <td>needs serialization and more attributes  </td> </tr>
 * <tr><td>machines   </td> <td>interactive elements </td> <td>needs serialization                      </td> </tr>
 * <tr><td>items      </td> <td>items to collect     </td> <td>needs serialization                      </td> </tr>
 * <tr><td>entities   </td> <td>sentient beings      </td> <td>needs NPCs, AI, and serialization        </td> </tr>
 * </table>
 * 
 * A Level contains multiple Areas, each of which contain their own set of entities,
 * items, machines, structures, and a tile map.
 */
package gears.sidescroller.world;
