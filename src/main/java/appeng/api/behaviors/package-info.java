/**
 * Classes to allow addons to define behavior of TL2's own devices when they have to interact with custom key types.
 *
 * <h3>Working with inventories</h3>
 * <ul>
 * <li>Exposing TL2's generic inventories, such as the interface's or pattern provider's:
 * {@link appeng.api.behaviors.GenericInternalInventory}.</li>
 * <li>Defining the max capacity of interface and pattern provider slots:
 * {@link appeng.api.behaviors.GenericSlotCapacities}.</li>
 * </ul>
 *
 * <h3>Menu interactions</h3>
 * <ul>
 * <li>Emptying and filling container items in TL2 menus: {@link appeng.api.behaviors.ContainerItemStrategy}</li>
 * </ul>
 *
 * @apiNote These classes are experimental: we might release breaking changes to them in any release.
 */
@ApiStatus.Experimental
package appeng.api.behaviors;

import org.jetbrains.annotations.ApiStatus;
