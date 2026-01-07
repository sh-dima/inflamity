{
	description = "A Minecraft plugin that updates the way fire works and makes it much more powerful.";

	inputs = {
		nixpkgs.url = "github:NixOS/nixpkgs/nixos-25.11";
		flake-utils.url = "github:numtide/flake-utils";
	};

	outputs = {
		nixpkgs,
		flake-utils,
		...
	}: flake-utils.lib.eachDefaultSystem (system:
			let
				pkgs = import nixpkgs {
					inherit system;
				};
			in {
				devShell = pkgs.mkShell {
					buildInputs = with pkgs; [
						git
						gradle
						udev
					];

					shellHook = ''
						export LD_LIBRARY_PATH=${pkgs.udev}/lib:$LD_LIBRARY_PATH
					'';
				};
			});
}
